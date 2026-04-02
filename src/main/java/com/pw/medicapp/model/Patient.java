package com.pw.medicapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pw.medicapp.model.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IMPORTANTE: Deve essere IDENTITY per Postgres
    @Column(name = "patient_id")
    @JsonIgnore
    private Integer patientId;
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
    @Column(name = "fiscal_code", nullable = false, unique = true)
    private String fiscalCode;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "treatments")
    private String treatments;
    @Column(name = "allergies")
    private String allergies;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private UserRole role;


}
