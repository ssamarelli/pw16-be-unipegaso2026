package com.pw.medicapp.service;

import com.pw.medicapp.DTO.HistoryDTO;
import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.mapper.UserMapper;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.repository.PatientRepository;
import com.pw.medicapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserMapper userMapper;

    // GET: Recupera la storia clinica
    public PatientDTO getPatientHistory(String fiscalCode) {
        Patient patient = patientRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato"));

        // Restituiamo il DTO che contiene diagnosi, allergie e trattamenti
        return userMapper.toDto(patient);
    }

    @Transactional
    public PatientDTO updatePatientHistory(String fiscalCode, HistoryDTO historyDTO) {
        Patient patient = patientRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new EntityNotFoundException("Paziente non trovato: " + fiscalCode));

        if (historyDTO.getDiagnosis() != null) {
            patient.setDiagnosis(historyDTO.getDiagnosis());
        }
        if (historyDTO.getTreatments() != null) {
            patient.setTreatments(historyDTO.getTreatments());
        }
        if (historyDTO.getAllergies() != null) {
            patient.setAllergies(historyDTO.getAllergies());
        }

        Patient saved = patientRepository.save(patient);
        return (PatientDTO) userMapper.toDto(saved);
    }
}
