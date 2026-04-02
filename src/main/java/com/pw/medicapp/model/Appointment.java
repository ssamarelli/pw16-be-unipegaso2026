package com.pw.medicapp.model;

import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.model.enums.AppointmentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId; // Cambia da String a int
    @Column(name = "appointment_date")
    private LocalDate appointmentDate;
    @Column(name = "appointment_time")
    private LocalTime appointmentTime;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    // Relazione N-1 con Patient
    @ManyToOne
    private Patient patient;
    // Relazione N-1 con Doctor
    @ManyToOne
    private Doctor doctor;

}
