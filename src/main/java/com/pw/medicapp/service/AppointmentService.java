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

        Appointment saved = appointmentRepository.save(appointment);

        // 3. INVIO MAIL (Logica aggiunta)
        try {
            mailService.sendAppointmentConfirmation(
                    patient.getEmail(),
                    patient.getFirstName(),
                    saved.getAppointmentDate().toString(),
                    saved.getAppointmentTime().toString(),
                    doctor.getLastName()
            );
        } catch (Exception e) {
            // Logga l'errore ma non bloccare la creazione dell'appuntamento se la mail fallisce
            System.err.println("Errore nell'invio della mail: " + e.getMessage());
        }

        return appointmentMapper.toDto(saved);
    }

    @Transactional
    public AppointmentDTO updateAppointment(Integer id, AppointmentDTO dto) {
        // 1. Recupero appuntamento esistente
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));

        // 2. Salviamo i vecchi valori per la mail
        String oldDate = existing.getAppointmentDate().toString();
        String oldTime = String.valueOf(existing.getAppointmentTime());

        // 3. Applichiamo le modifiche tramite Mapper
        appointmentMapper.updateEntityFromDto(dto, existing);

        // 4. Salvataggio
        Appointment updated = appointmentRepository.save(existing);

        // 5. Logica Invio Mail se data o ora sono cambiate
        if (!oldDate.equals(updated.getAppointmentDate().toString()) || !oldTime.equals(updated.getAppointmentTime())) {
            try {
                mailService.sendAppointmentUpdate(
                        updated.getPatient().getEmail(),
                        updated.getPatient().getFirstName(),
                        oldDate,
                        oldTime,
                        updated.getAppointmentDate().toString(),
                        String.valueOf(updated.getAppointmentTime()),
                        updated.getDoctor().getLastName()
                );
            } catch (Exception e) {
                System.err.println("Errore invio mail modifica: " + e.getMessage());
            }
        }

        return appointmentMapper.toDto(updated);
    }

    @Transactional
    public void deleteAppointment(Integer id) {
        // 1. Recuperiamo l'appuntamento completo prima di cancellarlo
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossibile eliminare: appuntamento non trovato"));

        // 2. Salviamo i dati necessari per la mail
        String patientEmail = appointment.getPatient().getEmail();
        String patientName = appointment.getPatient().getFirstName();
        String date = appointment.getAppointmentDate().toString();
        String time = String.valueOf(appointment.getAppointmentTime());
        String doctorName = appointment.getDoctor().getLastName();

        // 3. Eliminiamo il record dal DB
        appointmentRepository.delete(appointment);

        // 4. Inviamo la mail di notifica
        try {
            mailService.sendAppointmentCancellation(patientEmail, patientName, date, time, doctorName);
        } catch (Exception e) {
            // Logghiamo l'errore ma l'eliminazione resta valida
            System.err.println("Errore nell'invio della mail di cancellazione: " + e.getMessage());
        }
    }
}
