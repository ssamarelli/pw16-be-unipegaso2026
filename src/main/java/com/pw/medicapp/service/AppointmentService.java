package com.pw.medicapp.service;

import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.model.enums.AppointmentType;
import com.pw.medicapp.repository.AppointmentRepository;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.repository.PatientRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentService{

    @Autowired
    private MailService mailService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Recupera la lista di tutti gli appuntamenti
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments;
    }

    @Transactional
    public Appointment createAppointment(Appointment appointment, String patientFiscalCode, String doctorFiscalCode, AppointmentType type, @NotNull AppointmentStatus status) {

        Patient patient = patientRepository.findByFiscalCode(patientFiscalCode)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato con CF: " + patientFiscalCode));

        Doctor doctor = doctorRepository.findByFiscalCode(doctorFiscalCode)
                .orElseThrow(() -> new RuntimeException("Dottore non trovato con CF: " + doctorFiscalCode));

        appointment.setAppointmentId(null);
        appointment.setPatientFiscalCode(patient.getFiscalCode());           // mancava
        appointment.setDoctorFiscalCode(doctor.getFiscalCode());             // mancava
        appointment.setAppointmentType(type);      // mancava
        appointment.setAppointmentStatus(status);
        Appointment saved = appointmentRepository.save(appointment);

        // Invio mail
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

        return saved;
    }

    @Transactional
    public Appointment updateAppointment(Integer id, Appointment appointment,
                                         String doctorFiscalCode, String patientFiscalCode,
                                         AppointmentType type, AppointmentStatus status) {

        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));

        // Salvo vecchi valori per confronto
        LocalDate oldDate = existing.getAppointmentDate();
        LocalTime oldTime = existing.getAppointmentTime();
        String oldDoctorFc = existing.getDoctorFiscalCode();
        AppointmentStatus oldStatus = existing.getAppointmentStatus();

        // Update campi dal body (solo se non null)
        if (appointment.getAppointmentDate() != null) existing.setAppointmentDate(appointment.getAppointmentDate());
        if (appointment.getAppointmentTime() != null) existing.setAppointmentTime(appointment.getAppointmentTime());

        // Update da RequestParam
        if (type != null) existing.setAppointmentType(type);
        if (status != null) existing.setAppointmentStatus(status);

        // Cambio dottore
        if (doctorFiscalCode != null) {
            doctorRepository.findByFiscalCode(doctorFiscalCode)
                    .orElseThrow(() -> new RuntimeException("Dottore non trovato: " + doctorFiscalCode));
            existing.setDoctorFiscalCode(doctorFiscalCode);
        }

        // Cambio paziente
        if (patientFiscalCode != null) {
            patientRepository.findByFiscalCode(patientFiscalCode)
                    .orElseThrow(() -> new RuntimeException("Paziente non trovato: " + patientFiscalCode));
            existing.setPatientFiscalCode(patientFiscalCode);
        }

        Appointment updated = appointmentRepository.save(existing);

        // Recupero entità per la mail
        Patient patient = patientRepository.findByFiscalCode(updated.getPatientFiscalCode())
                .orElse(null);
        Doctor doctor = doctorRepository.findByFiscalCode(updated.getDoctorFiscalCode())
                .orElse(null);

        if (patient == null || doctor == null) return updated;

        DateTimeFormatter dFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fullPatientName = patient.getFirstName() + " " + patient.getLastName();
        String fullDoctorName = "Dott. " + doctor.getLastName() + " " + doctor.getFirstName();

        try {
            if (updated.getAppointmentStatus() == AppointmentStatus.CANCELLED
                    && oldStatus != AppointmentStatus.CANCELLED) {

                mailService.sendAppointmentCancellation(
                        updated.getAppointmentId(),
                        patient.getEmail(),
                        fullPatientName,
                        updated.getAppointmentDate().format(dFmt),
                        updated.getAppointmentTime().toString(),
                        fullDoctorName
                );

            } else if (updated.getAppointmentStatus() == AppointmentStatus.CONFIRMED
                    && oldStatus == AppointmentStatus.CANCELLED) {

                mailService.sendAppointmentConfirmation(
                        updated.getAppointmentId(),
                        patient.getEmail(),
                        fullPatientName,
                        updated.getAppointmentDate().format(dFmt),
                        updated.getAppointmentTime().toString(),
                        fullDoctorName,
                        updated.getAppointmentType().toString()
                );

            } else {
                boolean dateChanged = oldDate != null && !oldDate.equals(updated.getAppointmentDate());
                boolean timeChanged = oldTime != null && !oldTime.equals(updated.getAppointmentTime());
                boolean doctorChanged = oldDoctorFc != null && !oldDoctorFc.equals(updated.getDoctorFiscalCode());

                if (dateChanged || timeChanged || doctorChanged) {
                    mailService.sendAppointmentUpdate(
                            updated.getAppointmentId(),
                            patient.getEmail(),
                            fullPatientName,
                            oldDate.format(dFmt),
                            oldTime.toString(),
                            updated.getAppointmentDate().format(dFmt),
                            updated.getAppointmentTime().toString(),
                            fullDoctorName,
                            updated.getAppointmentType().toString()
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Errore invio mail: " + e.getMessage());
        }

        return updated;
    }

    @Scheduled(fixedRate = 60000) // Esegue ogni minuto
    @Transactional
    public void autoUpdateCompletedAppointments() {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> all = appointmentRepository.findAll();
        boolean changed = false;

        for (Appointment app : all) {
            if (app.getAppointmentStatus() == AppointmentStatus.CONFIRMED 
                && app.getAppointmentDate() != null 
                && app.getAppointmentTime() != null) {
                
                LocalDateTime appDateTime = LocalDateTime.of(app.getAppointmentDate(), app.getAppointmentTime());
                if (appDateTime.isBefore(now)) {
                    app.setAppointmentStatus(AppointmentStatus.COMPLETED);
                    changed = true;
                }
            }
        }
        
        if (changed) {
            appointmentRepository.saveAll(all);
        }
    }

}
