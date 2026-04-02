package com.pw.medicapp.service;

import com.pw.medicapp.exceptionHandler.UserException;
import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.enums.UserRole;
import com.pw.medicapp.repository.AppointmentRepository;
import com.pw.medicapp.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    public Patient getPatientByFiscalCode(String fiscalCode) {
        return patientRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato con codice fiscale: " + fiscalCode));
    }

    public List<Appointment> getAppointmentsByPatient(String fiscalCode) {
        if (!patientRepository.existsByFiscalCode(fiscalCode)) {
            throw new UserException("Paziente non trovato con codice fiscale: " + fiscalCode);
        }
        return appointmentRepository.findByPatientFiscalCode(fiscalCode);
    }

    @Transactional
    public Patient createPatient(Patient patient) {
        Optional<Patient> check = patientRepository.findByFiscalCode(patient.getFiscalCode());
        if(check.isPresent()) {
            throw new UserException("Il codice fiscale è già assegnato ad un paziente");
        }
        patient.setPatientId(null);
        patient.setRole(UserRole.PATIENT);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        Optional<Patient> check = patientRepository.findByFiscalCode(patient.getFiscalCode());
        if(check.isEmpty()) {
            throw new UserException("Non esiste un paziente con questo codice fiscale");
        }
        Patient existing = check.get();
        if (patient.getFirstName() != null) existing.setFirstName(patient.getFirstName());
        if (patient.getLastName() != null) existing.setLastName(patient.getLastName());
        if (patient.getEmail() != null) existing.setEmail(patient.getEmail());
        if (patient.getPhoneNumber() != null) existing.setPhoneNumber(patient.getPhoneNumber());
        if (patient.getAddress() != null) existing.setAddress(patient.getAddress());
        if (patient.getDob() != null) existing.setDob(patient.getDob());
        if (patient.getBirthplace() != null) existing.setBirthplace(patient.getBirthplace());
        if (patient.getDiagnosis() != null) existing.setDiagnosis(patient.getDiagnosis());
        if (patient.getTreatments() != null) existing.setTreatments(patient.getTreatments());
        if (patient.getAllergies() != null) existing.setAllergies(patient.getAllergies());


        return patientRepository.save(existing);
    }

    @Transactional
    public String deletePatient(String fiscalCode) {
        Optional<Patient> check = patientRepository.findByFiscalCode(fiscalCode);
        if(check.isEmpty()) {
            throw new UserException("Non esiste un paziente con questo codice fiscale");
        }
        patientRepository.deleteByFiscalCode(fiscalCode);
        Optional<Patient> deleted = patientRepository.findByFiscalCode(fiscalCode);
        if(deleted.isEmpty()) {
            return "Il paziente con codice fiscale  " +  fiscalCode + " è stato rimosso correttamente";
        } else
        {
            throw new UserException("Errore nella rimozione del paziente");
        }
    }


//    // GET: Recupera la storia clinica
//    public PatientDTO getPatientHistory(String fiscalCode) {
//        Patient patient = patientRepository.findByFiscalCode(fiscalCode)
//                .orElseThrow(() -> new RuntimeException("Paziente non trovato"));
//
//        // Restituiamo il DTO che contiene diagnosi, allergie e trattamenti
//        return userMapper.toDto(patient);
//    }


}
