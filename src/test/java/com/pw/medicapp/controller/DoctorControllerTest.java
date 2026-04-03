package com.pw.medicapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.service.DoctorService;
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

@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private DoctorRepository doctorRepository; // Injected in controller, needs to be mocked

    @Test
    public void shouldGetDoctorByFiscalCode() throws Exception {
        Doctor doc = new Doctor();
        doc.setFiscalCode("DOC123");
        doc.setFirstName("Dr. House");

        Mockito.when(doctorService.getDoctorByFiscalCode("DOC123")).thenReturn(doc);

        mockMvc.perform(get("/api/doctor/DOC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fiscalCode").value("DOC123"))
                .andExpect(jsonPath("$.firstName").value("Dr. House"));
    }

    @Test
    public void shouldCreateNewDoctor() throws Exception {
        Doctor doc = new Doctor();
        doc.setFiscalCode("DOC123");
        // Required fields per validation usually
        doc.setFirstName("House");
        doc.setLastName("MD");
        doc.setEmail("house@hospital.com");
        doc.setPhoneNumber("1234567890");

        Mockito.when(doctorService.createDoctor(any(Doctor.class))).thenReturn(doc);

        mockMvc.perform(post("/api/doctor/new-doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doc)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fiscalCode").value("DOC123"));
    }

    @Test
    public void shouldUpdateDoctor() throws Exception {
        Doctor doc = new Doctor();
        doc.setFiscalCode("DOC123");
        doc.setFirstName("Updated Name");

        Mockito.when(doctorService.updateDoctor(any(Doctor.class))).thenReturn(doc);

        mockMvc.perform(put("/api/doctor/update-doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated Name"));
    }

    @Test
    public void shouldDeleteDoctor() throws Exception {
        Mockito.when(doctorService.deleteDoctor("DOC123")).thenReturn("Success");

        mockMvc.perform(delete("/api/doctor/delete-doctor/DOC123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    public void shouldGetPatientsByDoctor() throws Exception {
        Mockito.when(doctorService.getPatientsByDoctor("DOC123")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/doctor/DOC123/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    public void shouldGetAppointmentsByDoctor() throws Exception {
        Mockito.when(doctorService.getAppointmentsByDoctor("DOC123")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/doctor/DOC123/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }
}
