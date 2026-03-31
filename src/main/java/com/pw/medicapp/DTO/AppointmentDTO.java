package com.pw.medicapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.model.enums.AppointmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    @JsonIgnore
    private Integer appointmentId;
    private LocalDate date;
    @Schema(type = "string", example = "18:30")
    private LocalTime time;
    @NotNull(message = "L'ID del dottore è obbligatorio")
    private Integer doctorId;  // Per la logica JS
    private String doctorName; // Per visualizzazione veloce
    @NotNull(message = "L'ID del paziente è obbligatorio")
    private Integer patientId;
    private String patientName;
    @JsonIgnore  // ← sparisce dal JSON e da Swagger
    private AppointmentType type;
    @JsonIgnore  // ← sparisce dal JSON e da Swagger
    private AppointmentStatus status;
}