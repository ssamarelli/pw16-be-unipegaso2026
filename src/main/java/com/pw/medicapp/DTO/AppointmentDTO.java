package com.pw.medicapp.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Integer appointmentId;
    private Date date;
    private String time;

    private Integer doctorId;  // Per la logica JS
    private String doctorName; // Per visualizzazione veloce

    private Integer patientId;
    private String patientName;
}