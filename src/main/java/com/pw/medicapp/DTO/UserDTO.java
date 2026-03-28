package com.pw.medicapp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pw.medicapp.model.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId; // Sarà ignorato in fase di insert

    @NotBlank(message = "Il nome è obbligatorio")
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String lastName;

    @Email(message = "Formato email non valido")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;

    private String phoneNumber;
    private String address;
    @NotNull(message = "La data di nascita è obbligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    @NotBlank(message = "Il luogo di nascita è obbligatorio")
    private String birthplace;

    @NotBlank(message = "Il codice fiscale è mandatorio")
    @Size(min = 16, max = 16, message = "Il codice fiscale deve essere di 16 caratteri")
    private String fiscalCode;

    @NotNull(message = "Il ruolo è obbligatorio")
    private UserRole role;
}