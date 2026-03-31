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
        // 1. Recupero entità (come fatto in precedenza)
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato"));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Dottore non trovato"));

        // 2. Mapping e Salvataggio
        Appointment appointment = appointmentMapper.toEntity(dto);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointment.setAppointmentType(dto.getType()); // ← aggiungi questa riga


        Appointment saved = appointmentRepository.save(appointment);

        // 3. INVIO MAIL (Logica aggiunta)
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormat = saved.getAppointmentDate().format(formatter);
            mailService.sendAppointmentConfirmation(
                    appointment.getAppointmentId(),
                    patient.getEmail(),
                    patient.getFirstName(),
                    saved.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    saved.getAppointmentTime().toString(),
                    doctor.getLastName(),
                    saved.getAppointmentType().toString()
            );
        } catch (Exception e) {
            // Logga l'errore ma non bloccare la creazione dell'appuntamento se la mail fallisce
            System.err.println("Errore nell'invio della mail: " + e.getMessage());
        }

        return appointmentMapper.toDto(saved);
    }

    @Transactional
    public AppointmentDTO updateAppointment(Integer id, AppointmentDTO dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));

        // Salviamo gli oggetti originali per il confronto
        LocalDate oldDate = existing.getAppointmentDate();
        LocalTime oldTime = existing.getAppointmentTime();

        appointmentMapper.updateEntityFromDto(dto, existing);

        if (dto.getType() != null) existing.setAppointmentType(dto.getType());
        if (dto.getStatus() != null) existing.setAppointmentStatus(dto.getStatus());

        Appointment updated = appointmentRepository.save(existing);

        // Verifichiamo se è cambiato qualcosa
        if (!oldDate.equals(updated.getAppointmentDate()) || !oldTime.equals(updated.getAppointmentTime())) {
            try {
                DateTimeFormatter dFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                mailService.sendAppointmentUpdate(
                        updated.getAppointmentId(),
                        updated.getPatient().getEmail(),
                        updated.getPatient().getFirstName(),
                        oldDate.format(dFmt),
                        oldTime.toString(),
                        updated.getAppointmentDate().format(dFmt),
                        updated.getAppointmentTime().toString(),
                        updated.getDoctor().getLastName(),
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
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossibile eliminare: appuntamento non trovato"));

        // Prepariamo i dati prima dell'eliminazione
        String patientEmail = appointment.getPatient().getEmail();
        String patientName = appointment.getPatient().getFirstName();
        String formattedDate = appointment.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String formattedTime = appointment.getAppointmentTime().toString();
        String doctorLastName = appointment.getDoctor().getLastName();
        Integer appId = appointment.getAppointmentId();

        appointmentRepository.delete(appointment);

        try {
            mailService.sendAppointmentCancellation(appId, patientEmail, patientName, formattedDate, formattedTime, doctorLastName);
        } catch (Exception e) {
            System.err.println("Errore nell'invio della mail di cancellazione: " + e.getMessage());
        }
    }
}
