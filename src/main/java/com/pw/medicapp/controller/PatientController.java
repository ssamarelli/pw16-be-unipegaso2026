package com.pw.medicapp.controller;

import com.pw.medicapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

//GET /api/patient/{id}/history

//POST /api/patient/{id}/history

//GET /api/patient/{id}/documents

//POST /api/patient/{id}/documents/upload

//DELETE /api/patient/{id}/documents/{docId}

}
