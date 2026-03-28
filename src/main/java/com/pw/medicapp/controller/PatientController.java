package com.pw.medicapp.controller;

import com.pw.medicapp.DTO.HistoryDTO;
import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.service.PatientService;
import com.pw.medicapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @PutMapping("/{fiscalCode}/history")
    public ResponseEntity<PatientDTO> updateHistory(
            @PathVariable String fiscalCode,
            @RequestBody HistoryDTO historyDTO) {
        return ResponseEntity.ok(patientService.updatePatientHistory(fiscalCode, historyDTO));
    }

    @GetMapping("/{fiscalCode}/get-history")
    public ResponseEntity<PatientDTO> getHistory(@PathVariable String fiscalCode) {
        return ResponseEntity.ok(patientService.getPatientHistory(fiscalCode));
    }

}