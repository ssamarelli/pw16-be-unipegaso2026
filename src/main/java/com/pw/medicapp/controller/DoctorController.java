package com.pw.medicapp.controller;

import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.service.DoctorService;
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
    private UserService userService;

    @Autowired
    private DoctorService doctorService;


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
    public String deleteDoctor(@Valid @RequestParam String fiscalCode) {
        return doctorService.deleteDoctor(fiscalCode);
    }


//    //GET /api/doctor/{id}/appointments
//    @GetMapping("/{fiscalCode}/appointments")
//    public ResponseEntity<List<AppointmentDTO>> getDoctorAppointments(@PathVariable String fiscalCode) {
//        List<AppointmentDTO> appointments = doctorService.getAppointmentsByDoctor(fiscalCode);
//        return ResponseEntity.ok(appointments);
//    }
//
//     //GET /api/doctor/{id}/patients
//     @GetMapping("/{fiscalCode}/patients")
//     public ResponseEntity<List<PatientDTO>> getDoctorPatients(@PathVariable String fiscalCode) {
//         List<PatientDTO> patients = doctorService.getPatientsByDoctor(fiscalCode);
//         return ResponseEntity.ok(patients);
//     }
//
//
//    @PutMapping("/{fiscalCode}/update")
//    public ResponseEntity<UserDTO> updateDoctor(
//            @PathVariable String fiscalCode,
//            @RequestBody DoctorDTO doctorDTO) {
//        return ResponseEntity.ok(userService.updateUser(fiscalCode, doctorDTO));
//    }
}
