// com/pw/medicapp/DTO/HistoryDTO.java
package com.pw.medicapp.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private String diagnosis;
    private String treatments;
    private String allergies;
}