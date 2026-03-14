package com.pw.medicapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Doctor extends User{

    private String specialization;
    private String medical_license_number;
}
