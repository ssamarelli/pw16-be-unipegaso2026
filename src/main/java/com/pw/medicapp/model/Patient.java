package com.pw.medicapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Patient extends User{

    private String diagnosis;
    private String treatments;
    private String allergies;
}
