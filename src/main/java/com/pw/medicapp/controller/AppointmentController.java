//package com.pw.medicapp.controller;
//
//
//import com.pw.medicapp.DTO.AppointmentDTO;
//import com.pw.medicapp.model.enums.AppointmentStatus;
//import com.pw.medicapp.model.enums.AppointmentType;
//import com.pw.medicapp.service.AppointmentService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/appointment")
//public class AppointmentController {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
////GET /api/appointments/list
//    @GetMapping("/list")
//    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
//        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
//        return ResponseEntity.ok(appointments);
//    }
//
////POST /api/appointments
//@PostMapping("/new-appointment")
//public ResponseEntity<AppointmentDTO> createAppointment(
//        @Valid @RequestBody AppointmentDTO appointmentDTO,
//        @RequestParam AppointmentType type,
//        @RequestParam(required = false, defaultValue = "CONFIRMED") AppointmentStatus status) {
//    appointmentDTO.setType(type);
//    appointmentDTO.setStatus(status);
//    AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
//    return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
//}
//
//    // PUT /api/appointments/{id}
//    @PutMapping("/update/{id}")
//    public ResponseEntity<AppointmentDTO> updateAppointment(
//            @PathVariable Integer id,
//            @RequestBody AppointmentDTO dto,
//            @RequestParam AppointmentType type,
//            @RequestParam(required = false, defaultValue = "CONFIRMED") AppointmentStatus status) {
//        dto.setType(type);
//        dto.setStatus(status);
//        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
//    }
//
//    // DELETE /api/appointments/{id}
//    //necessaria??????????
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {
//        appointmentService.deleteAppointment(id);
//        return ResponseEntity.noContent().build();
//    }
//
//}
