package com.pw.medicapp.service;

import com.pw.medicapp.DTO.AppointmentDTO;
import com.pw.medicapp.mapper.AppointmentMapper;
import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.repository.AppointmentRepository;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService{

    @Autowired
    private MailService mailService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Recupera la lista di tutti gli appuntamenti
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        // 1. Recupero entità tramite Codice Fiscale
        Patient patient = patientRepository.findByFiscalCode(dto.getPatientFiscalCode())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato con CF: " + dto.getPatientFiscalCode()));

        Doctor doctor = doctorRepository.findByFiscalCode(dto.getDoctorFiscalCode())
                .orElseThrow(() -> new RuntimeException("Dottore non trovato con CF: " + dto.getDoctorFiscalCode()));

        // 2. Mapping e Salvataggio
        Appointment appointment = appointmentMapper.toEntity(dto);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointment.setAppointmentType(dto.getType());

        Appointment saved = appointmentRepository.save(appointment);

        // 3. INVIO MAIL (Con ordine parametri corretto e formato ITA)
        try {
            DateTimeFormatter dFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fullDoctorName = "Dott. " + doctor.getLastName() + " " + doctor.getFirstName();
            String fullPatientName = patient.getFirstName() + " " + patient.getLastName();
            mailService.sendAppointmentConfirmation(
                    saved.getAppointmentId(),
                    patient.getEmail(),
                    fullPatientName,
                    saved.getAppointmentDate().format(dFmt),
                    saved.getAppointmentTime().toString(),
                    fullDoctorName,
                    saved.getAppointmentType().toString()
            );
        } catch (Exception e) {
            System.err.println("Errore nell'invio della mail: " + e.getMessage());
        }

        return appointmentMapper.toDto(saved);
    }

    @Transactional
    public AppointmentDTO updateAppointment(Integer id, AppointmentDTO dto) {
        // 1. Recupero appuntamento esistente
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));

        // 2. Salviamo i vecchi valori (oggetti) per il confronto
        LocalDate oldDate = existing.getAppointmentDate();
        LocalTime oldTime = existing.getAppointmentTime();
        Doctor oldDoctor = existing.getDoctor(); // Salviamo il riferimento al vecchio medico

        // 3. Update dei campi base tramite Mapper
        appointmentMapper.updateEntityFromDto(dto, existing);

        // 4. Gestione cambio Dottore tramite Codice Fiscale
        if (dto.getDoctorFiscalCode() != null && !dto.getDoctorFiscalCode().equals(oldDoctor.getFiscalCode())) {
            Doctor newDoctor = doctorRepository.findByFiscalCode(dto.getDoctorFiscalCode())
                    .orElseThrow(() -> new RuntimeException("Nuovo dottore non trovato: " + dto.getDoctorFiscalCode()));
            existing.setDoctor(newDoctor);
        }

        // 5. Gestione cambio Paziente (opzionale, solitamente il paziente non cambia per lo stesso ID)
        if (dto.getPatientFiscalCode() != null && !dto.getPatientFiscalCode().equals(existing.getPatient().getFiscalCode())) {
            Patient newPatient = patientRepository.findByFiscalCode(dto.getPatientFiscalCode())
                    .orElseThrow(() -> new RuntimeException("Nuovo paziente non trovato: " + dto.getPatientFiscalCode()));
            existing.setPatient(newPatient);
        }

        if (dto.getType() != null) existing.setAppointmentType(dto.getType());
        if (dto.getStatus() != null) existing.setAppointmentStatus(dto.getStatus());

        // 6. Salvataggio
        Appointment updated = appointmentRepository.save(existing);

        // 7. Logica Mail: Scatta se cambia Data, Ora O Medico
        boolean dateChanged = !oldDate.equals(updated.getAppointmentDate());
        boolean timeChanged = !oldTime.equals(updated.getAppointmentTime());
        boolean doctorChanged = !oldDoctor.equals(updated.getDoctor());

        if (dateChanged || timeChanged || doctorChanged) {
            try {
                DateTimeFormatter dFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                String fullPatientName = updated.getPatient().getFirstName() + " " + updated.getPatient().getLastName();
                String fullDoctorName = "Dott. " + updated.getDoctor().getLastName() + " " + updated.getDoctor().getFirstName();

                mailService.sendAppointmentUpdate(
                        updated.getAppointmentId(),
                        updated.getPatient().getEmail(),
                        fullPatientName,
                        oldDate.format(dFmt),
                        oldTime.toString(),
                        updated.getAppointmentDate().format(dFmt),
                        updated.getAppointmentTime().toString(),
                        fullDoctorName,
                        updated.getAppointmentType().toString()
                );
            } catch (Exception e) {
                System.err.println("Errore invio mail modifica: " + e.getMessage());
            }
        }

        return appointmentMapper.toDto(updated);
    }

    @Transactional
    public void deleteAppointment(Integer id) {
        // 1. Recupero l'appuntamento completo
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossibile eliminare: appuntamento non trovato"));

        // 2. Prepariamo i dati PRIMA dell'eliminazione
        // Usiamo la stessa logica dei nomi completi usata nell'update
        String fullPatientName = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
        String fullDoctorName = "Dott. " + appointment.getDoctor().getLastName() + " " + appointment.getDoctor().getFirstName();

        String patientEmail = appointment.getPatient().getEmail();

        // Formattazione data (formato italiano)
        DateTimeFormatter dFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = appointment.getAppointmentDate().format(dFmt);
        String formattedTime = appointment.getAppointmentTime().toString();

        Integer appId = appointment.getAppointmentId();

        // 3. Eliminazione fisica dal DB
        appointmentRepository.delete(appointment);

        // 4. Notifica via mail (passiamo i nomi completi)
        try {
            mailService.sendAppointmentCancellation(
                    appId,
                    patientEmail,
                    fullPatientName,
                    formattedDate,
                    formattedTime,
                    fullDoctorName
            );
        } catch (Exception e) {
            System.err.println("Errore nell'invio della mail di cancellazione: " + e.getMessage());
        }
    }
}
