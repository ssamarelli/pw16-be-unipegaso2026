package com.pw.medicapp.controller;

import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.service.DoctorService;
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

import java.util.List;

@RestController
@RequestMapping("api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/{fiscalCode}")
    public ResponseEntity<Doctor> getDoctorByFiscalCode(@PathVariable String fiscalCode) {
        return ResponseEntity.ok(doctorService.getDoctorByFiscalCode(fiscalCode));
    }

    @PostMapping("/new-doctor")
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody Doctor doctor) {
        Doctor created = doctorService.createDoctor(doctor);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //put
    @PutMapping("/update-doctor")
    public ResponseEntity<Doctor> updateDoctor(@Valid @RequestBody Doctor doctor) {
        Doctor updated = doctorService.updateDoctor(doctor);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete-doctor/{fiscalCode}")
    public String deleteDoctor(@Valid @PathVariable String fiscalCode) {
        return doctorService.deleteDoctor(fiscalCode);
    }


    @GetMapping("/{fiscalCode}/patients")
    public List<Patient> getPatientsByDoctor(@PathVariable String fiscalCode) {
        return doctorService.getPatientsByDoctor(fiscalCode);
    }

    @GetMapping("/{fiscalCode}/appointments")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String fiscalCode) {
        return doctorService.getAppointmentsByDoctor(fiscalCode);
    }
}
