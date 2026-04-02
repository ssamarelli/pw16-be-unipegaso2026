package com.pw.medicapp.controller;

import com.pw.medicapp.model.Patient;
import com.pw.medicapp.service.PatientService;
import com.pw.medicapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @PostMapping("/new-patient")
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        Patient created = patientService.createPatient(patient);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //put
    @PutMapping("/update-patient")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient) {
        Patient updated = patientService.updatePatient(patient);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete-patient/{fiscalCode}")
    public String deletePatient(@Valid @RequestParam String fiscalCode) {
        return patientService.deletePatient(fiscalCode);
    }


//    @PutMapping("/{fiscalCode}/history")
//    public ResponseEntity<PatientDTO> updateHistory(
//            @PathVariable String fiscalCode,
//            @RequestBody PatientDTO patientDTO) {
//        return ResponseEntity.ok(patientService.updatePatientHistory(fiscalCode, patientDTO));
//    }
//
//    @GetMapping("/{fiscalCode}/get-history")
//    public ResponseEntity<PatientDTO> getHistory(@PathVariable String fiscalCode) {
//        return ResponseEntity.ok(patientService.getPatientHistory(fiscalCode));
//    }

}