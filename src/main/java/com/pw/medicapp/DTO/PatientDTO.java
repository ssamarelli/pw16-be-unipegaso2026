package com.pw.medicapp.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PatientDTO extends UserDTO {
    private String diagnosis;
    private String treatments;
    private String allergies;
}