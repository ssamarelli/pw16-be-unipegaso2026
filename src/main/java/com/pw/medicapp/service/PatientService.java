package com.pw.medicapp.service;

import com.pw.medicapp.exceptionHandler.UserException;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.enums.UserRole;
import com.pw.medicapp.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public Patient createPatient(Patient patient) {
        Optional<Patient> check = patientRepository.findByFiscalCode(patient.getFiscalCode());
        if(check.isPresent()) {
            throw new UserException("Patient already exist");
        }
        patient.setPatientId(null);
        patient.setRole(UserRole.PATIENT);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        Optional<Patient> check = patientRepository.findByFiscalCode(patient.getFiscalCode());
        if(check.isEmpty()) {
            throw new UserException("Patient doesn't exist");
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
            throw new UserException("Patient doesn't exist");
        }
        patientRepository.deleteByFiscalCode(fiscalCode);
        Optional<Patient> deleted = patientRepository.findByFiscalCode(fiscalCode);
        if(deleted.isEmpty()) {
            return "Patient " +  fiscalCode + " removed correctly";
        } else
        {
            throw new UserException("Patient has not been removed correctly");
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
