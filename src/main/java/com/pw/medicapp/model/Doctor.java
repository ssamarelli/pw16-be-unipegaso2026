package com.pw.medicapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "Doctors")
public class Doctor extends User{

    @Column(name = "specialization")
    private String specialization;
    @Column(name = "medical_license_number")
    private String medicalLicenseNumber;



}
