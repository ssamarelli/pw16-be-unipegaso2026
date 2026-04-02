package com.pw.medicapp.controller;

import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.model.enums.AppointmentType;
import com.pw.medicapp.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

//GET /api/appointments/list
    @GetMapping("/list")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

//POST /api/appointments
@PostMapping("/new-appointment")
public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody Appointment appointment,
                                                     @RequestParam @NotNull String patientFiscalCode,
                                                     @RequestParam @NotNull String doctorFiscalCode,
                                                     @RequestParam @NotNull AppointmentType type,
                                                     @RequestParam @NotNull AppointmentStatus status) {
    Appointment created = appointmentService.createAppointment(appointment, patientFiscalCode, doctorFiscalCode, type, status);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
}


    @PutMapping("/update/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Integer id,
            @RequestBody Appointment appointment,
            @RequestParam(required = false) String doctorFiscalCode,
            @RequestParam(required = false) String patientFiscalCode,
            @RequestParam AppointmentType type,
            @RequestParam @NotNull AppointmentStatus status) {

        Appointment updated = appointmentService.updateAppointment(id, appointment, doctorFiscalCode, patientFiscalCode, type, status);
        return ResponseEntity.ok(updated);
    }
}
