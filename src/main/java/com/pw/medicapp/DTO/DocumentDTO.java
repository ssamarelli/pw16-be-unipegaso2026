package com.pw.medicapp.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class DocumentDTO {
    private int documentId;
    private String name;
    private String type;
    private Date uploadDate;

    private int patientId;
    private String patientFullName; // Comodo per il frontend
}