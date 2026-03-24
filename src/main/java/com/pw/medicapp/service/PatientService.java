package com.pw.medicapp.service;

import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.mapper.UserMapper;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.repository.PatientRepository;
import com.pw.medicapp.repository.UserRepository;
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

    // POST: Salva o aggiorna la storia clinica
    @Transactional
    public PatientDTO updatePatientHistory(String fiscalCode, PatientDTO historyDto) {
        Patient patient = patientRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato"));

        // Aggiorniamo solo i campi clinici
        patient.setDiagnosis(historyDto.getDiagnosis());
        patient.setTreatments(historyDto.getTreatments());
        patient.setAllergies(historyDto.getAllergies());

        return userMapper.toDto(patientRepository.save(patient));
    }
}
