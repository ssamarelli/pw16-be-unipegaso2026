package com.pw.medicapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @Test
    public void shouldGetPatientByFiscalCode() throws Exception {
        Patient pat = new Patient();
        pat.setFiscalCode("PTN123");
        pat.setFirstName("John");

        Mockito.when(patientService.getPatientByFiscalCode("PTN123")).thenReturn(pat);

        mockMvc.perform(get("/api/patient/PTN123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fiscalCode").value("PTN123"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void shouldGetAppointmentsByPatient() throws Exception {
        Mockito.when(patientService.getAppointmentsByPatient("PTN123")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/patient/PTN123/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    public void shouldCreateNewPatient() throws Exception {
        Patient pat = new Patient();
        pat.setFiscalCode("PTN123");
        pat.setFirstName("John");

        Mockito.when(patientService.createPatient(any(Patient.class))).thenReturn(pat);

        mockMvc.perform(post("/api/patient/new-patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pat)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fiscalCode").value("PTN123"));
    }

    @Test
    public void shouldUpdatePatient() throws Exception {
        Patient pat = new Patient();
        pat.setFiscalCode("PTN123");
        pat.setFirstName("Updated John");

        Mockito.when(patientService.updatePatient(any(Patient.class))).thenReturn(pat);

        mockMvc.perform(put("/api/patient/update-patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated John"));
    }

    @Test
    public void shouldDeletePatient() throws Exception {
        Mockito.when(patientService.deletePatient("PTN123")).thenReturn("Deleted");

        mockMvc.perform(delete("/api/patient/delete/PTN123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}
