package com.pw.medicapp.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Integer appointmentId;
    private Date date;
    private String time;
    private String type;
    private String status;

    private PatientDTO patient;
    private DoctorDTO doctor;
}