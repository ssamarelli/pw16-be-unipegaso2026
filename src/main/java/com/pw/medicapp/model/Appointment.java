package com.pw.medicapp.model;

import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.model.enums.AppointmentType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
public class Appointment {


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String appointmentId;
    private Date appointmentDate;
    private Time appointmentTime;
    private AppointmentType appointmentType;
    private AppointmentStatus appointmentStatus;

}
