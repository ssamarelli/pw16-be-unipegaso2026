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
@Table(name = "Patients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User{

    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "treatments")
    private String treatments;
    @Column(name = "allergies")
    private String allergies;
}
