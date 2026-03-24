//package com.pw.medicapp.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//import java.util.Date;
//
//@Data
//@Entity
//@Table(name = "Documents")
//public class Attachment{
//
//    @jakarta.persistence.Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "attachment_id")
//    private Integer attachmentId;
//    @Column(name = "file_name")
//    private String fileName;
//    @Column(name = "upload_date")
//    private Date uploadDate;
//    @Column(name = "file_type")
//    private String fileType;
//    @ManyToOne
//    @JoinColumn(name = "patient_id", referencedColumnName = "user_id")
//    private Patient patient;
//
//}
