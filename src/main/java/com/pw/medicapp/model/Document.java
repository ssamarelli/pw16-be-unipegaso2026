package com.pw.medicapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Document{

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int documentId;
    private String fileName;
    private Date uploadDate;
    private String fileType;

}
