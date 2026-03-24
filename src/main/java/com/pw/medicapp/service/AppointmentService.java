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
import com.pw.medicapp.repository.UserRepository;
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
        // 1. Cerchiamo l'appuntamento esistente
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato con ID: " + id));

        // 2. Usiamo il mapper per aggiornare l'entità esistente con i dati del DTO
        // Nota: il mapper ignorerà l'ID e i campi nulli se configurato correttamente
        appointmentMapper.updateEntityFromDto(dto, existingAppointment);

        // 3. Salvataggio (l'update su DB avviene automaticamente grazie a @Transactional)
        Appointment updated = appointmentRepository.save(existingAppointment);
        return appointmentMapper.toDto(updated);
    }

    @Transactional
    public void deleteAppointment(Integer id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: appuntamento non trovato");
        }
        appointmentRepository.deleteById(id);
    }
}
