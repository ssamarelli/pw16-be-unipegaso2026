package com.pw.medicapp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Schema(type = "string", example = "31/12/2026")
    private LocalDate date;
    @Schema(type = "string", example = "18:30")
    private LocalTime time;
    @NotNull(message = "Il Codice Fiscale del dottore è obbligatorio")
    private String doctorFiscalCode;
    @NotNull(message = "Il Codice Fiscale del paziente è obbligatorio")
    private String patientFiscalCode;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String doctorName;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String patientName;
    @JsonIgnore  // ← sparisce dal JSON e da Swagger
    private AppointmentType type;
    @JsonIgnore  // ← sparisce dal JSON e da Swagger
    private AppointmentStatus status;
}