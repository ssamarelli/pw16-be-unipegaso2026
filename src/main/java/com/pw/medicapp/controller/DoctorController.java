package com.pw.medicapp.controller;

import com.pw.medicapp.DTO.AppointmentDTO;
import com.pw.medicapp.DTO.DoctorDTO;
import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.repository.UserRepository;
import com.pw.medicapp.service.DoctorService;
import com.pw.medicapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/doctor")
public class DoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    //GET /api/doctor/all
    @GetMapping("list")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

     //GET /api/doctor/{id}/appointments
    @GetMapping("/{fiscalCode}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getDoctorAppointments(@PathVariable String fiscalCode) {
        List<AppointmentDTO> appointments = doctorService.getAppointmentsByDoctor(fiscalCode);
        return ResponseEntity.ok(appointments);
    }

     //GET /api/doctor/{id}/patients
     @GetMapping("/{fiscalCode}/patients")
     public ResponseEntity<List<PatientDTO>> getDoctorPatients(@PathVariable String fiscalCode) {
         List<PatientDTO> patients = doctorService.getPatientsByDoctor(fiscalCode);
         return ResponseEntity.ok(patients);
     }


    @PutMapping("/{fiscalCode}/update")
    public ResponseEntity<UserDTO> updateDoctor(
            @PathVariable String fiscalCode,
            @RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(userService.updateUser(fiscalCode, doctorDTO));
    }
}
