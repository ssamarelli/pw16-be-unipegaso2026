package com.pw.medicapp.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentRequest {
    private Date date;
    private String time;
    private String type;
    private int patientId;
    private int doctorId;
}