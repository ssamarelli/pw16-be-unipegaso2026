//package com.pw.medicapp.controller;
//
//import com.pw.medicapp.service.PatientService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/patient")
//public class PatientController {
//
//    @Autowired
//    private PatientService patientService;
//
//    @GetMapping("{id}")
//    public ResponseEntity<?> getPatient(@PathVariable int id) {
//        return patientService.getPatientById(id);
//    }
//
//    @PostMapping("create-patient")
//    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
//        return patientService.createPatient(patient);
//    }
//
//    @PutMapping("update-patient/{id}")
//    public ResponseEntity<?> updatePatient(@PathVariable int id,@RequestBody Patient patient) {
//        return patientService.updatePatient(id, patient);
//    }
//
//    @DeleteMapping("delete-patient")
//    public ResponseEntity<?> deletePatient(@PathVariable int id) {
//        return patientService.deletePatient(id);
//    }
//}
