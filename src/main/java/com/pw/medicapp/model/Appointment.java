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
import java.util.Date;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int appointmentId; // Cambia da String a int
    @Column(name = "appointment_date")
    private Date appointmentDate;
    @Column(name = "appointment_time")
    private Time appointmentTime;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    // Relazione N-1 con Patient
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
    private Patient patient;

    // Relazione N-1 con Doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "user_id")
    private Doctor doctor;

}
