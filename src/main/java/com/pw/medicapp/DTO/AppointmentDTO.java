package com.pw.medicapp.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Integer appointmentId;
    private Date date;
    private String time;
    @NotNull(message = "L'ID del dottore è obbligatorio"
    private Integer doctorId;  // Per la logica JS
    private String doctorName; // Per visualizzazione veloce
    @NotNull(message = "L'ID del paziente è obbligatorio")
    private Integer patientId;
    private String patientName;
}