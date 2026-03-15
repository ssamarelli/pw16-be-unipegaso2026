package com.pw.medicapp.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorDTO extends UserDTO {
    private String specialization;
    private String medicalLicenseNumber;
}