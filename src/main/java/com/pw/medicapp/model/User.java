package com.pw.medicapp.model;

import com.pw.medicapp.model.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.Date;
@Data
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IMPORTANTE: Deve essere IDENTITY per Postgres
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "first_name")
     private String firstName;
    @Column(name = "last_name")
     private String lastName;
    @Column(name = "email")
     private String email;
    @Column(name = "phone_number")
     private String phoneNumber;
    @Column(name = "address")
     private String address;
    @Column(name = "dob")
     private LocalDate dob;
    @Column(name = "birthplace")
    private String birthplace;
    @Column(name = "fiscal_code", nullable = false, unique = true) // Vincolo anche a livello DB
     private String fiscalCode;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
     private UserRole role;
}
