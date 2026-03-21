package com.pw.medicapp.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class DocumentDTO {
    private Integer documentId;
    private String fileName;
    private String type;
    private Date uploadDate;

    private Integer patientId;
    private String patientFullName; // Comodo per il frontend
}