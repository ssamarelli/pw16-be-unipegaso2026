package com.pw.medicapp.controller;

import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.service.PatientService;
import com.pw.medicapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PutMapping("/{fiscalCode}")
    public ResponseEntity<UserDTO> updatePatient(
            @PathVariable String fiscalCode,
            @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(userService.updateUser(fiscalCode, patientDTO));
    }
//GET /api/patient/{id}/history

//POST /api/patient/{id}/history

//GET /api/patient/{id}/documents

//POST /api/patient/{id}/documents/upload

//DELETE /api/patient/{id}/documents/{docId}

}
