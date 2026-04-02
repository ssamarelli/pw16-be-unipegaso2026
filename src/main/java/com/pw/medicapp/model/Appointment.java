package com.pw.medicapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.model.enums.AppointmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId; // Cambia da String a int
    @Column(name = "appointment_date")
    @Schema(type = "string", pattern = "dd/MM/yyyy", example = "15/04/2026")
    private LocalDate appointmentDate;
    @Column(name = "appointment_time")
    @Schema(type = "string", pattern = "HH:mm", example = "14:30")
    private LocalTime appointmentTime;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    @Column(name = "patient_fc")
    private String patientFiscalCode;
    @Column(name = "doctor_fc")
    private String doctorFiscalCode;

}
