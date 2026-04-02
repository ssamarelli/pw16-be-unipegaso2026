package com.pw.medicapp.model;

import com.pw.medicapp.model.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class User {

     private String firstName;
     private String lastName;
     private String email;
     private String phoneNumber;
    @Column(name = "fiscal_code", nullable = false, unique = true) // Vincolo anche a livello DB
     private String fiscalCode;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
     private UserRole role;
}
