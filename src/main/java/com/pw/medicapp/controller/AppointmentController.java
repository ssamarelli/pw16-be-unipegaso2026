//package com.pw.medicapp.controller;
//
//import com.pw.medicapp.DTO.AppointmentDTO;
//import com.pw.medicapp.service.AppointmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/appointment")
//public class AppointmentController {
//
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//    @PostMapping("/new-appointment")
//    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
//        return appointmentService.schedule();
//    }
//
//    @PutMapping("/update-appointment")
//    public ResponseEntity<?> updateAppointment(@RequestBody int appointmentid, AppointmentDTO appointmentDTO) {
//        return appointmentService.reschedule();
//    }
//
//    @DeleteMapping("/delete-appointment")
//    public ResponseEntity<?> deleteAppointment(@RequestBody int appointmentid, AppointmentDTO appointmentDTO) {
//        return appointmentService.cancel();
//    }
//
//    @GetMapping("/list-appointments")
//    public ResponseEntity<?> listAppointments(@RequestBody int appointmentid, AppointmentDTO appointmentDTO) {
//        return appointmentService.listByPatiendId();
//    }
//
//}
