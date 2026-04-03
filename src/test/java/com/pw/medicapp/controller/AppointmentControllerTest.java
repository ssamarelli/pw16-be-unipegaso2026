package com.pw.medicapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.enums.AppointmentStatus;
import com.pw.medicapp.model.enums.AppointmentType;
import com.pw.medicapp.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    public void shouldGetAllAppointments() throws Exception {
        Appointment apt = new Appointment();
        apt.setAppointmentId(1);
        apt.setAppointmentDate(LocalDate.now());

        Mockito.when(appointmentService.getAllAppointments()).thenReturn(Collections.singletonList(apt));

        mockMvc.perform(get("/api/appointment/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].appointmentId").value(1));
    }

    @Test
    public void shouldCreateAppointment() throws Exception {
        Appointment apt = new Appointment();
        apt.setAppointmentDate(LocalDate.now());
        apt.setAppointmentTime(LocalTime.of(10, 30));

        Appointment savedApt = new Appointment();
        savedApt.setAppointmentId(10);
        savedApt.setAppointmentStatus(AppointmentStatus.CONFIRMED);

        Mockito.when(appointmentService.createAppointment(
                any(Appointment.class),
                eq("PTN123"),
                eq("DOC123"),
                eq(AppointmentType.CHECKUP),
                eq(AppointmentStatus.CONFIRMED)
        )).thenReturn(savedApt);

        mockMvc.perform(post("/api/appointment/new-appointment")
                .param("patientFiscalCode", "PTN123")
                .param("doctorFiscalCode", "DOC123")
                .param("type", "CHECKUP")
                .param("status", "CONFIRMED")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apt)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.appointmentId").value(10))
                .andExpect(jsonPath("$.appointmentStatus").value("CONFIRMED"));
    }

    @Test
    public void shouldUpdateAppointment() throws Exception {
        Appointment apt = new Appointment();
        apt.setAppointmentDate(LocalDate.now().plusDays(1));

        Appointment updatedApt = new Appointment();
        updatedApt.setAppointmentId(10);
        updatedApt.setAppointmentStatus(AppointmentStatus.COMPLETED);

        Mockito.when(appointmentService.updateAppointment(
                eq(10),
                any(Appointment.class),
                eq("DOC123"),
                eq("PTN123"),
                eq(AppointmentType.CONSULTATION),
                eq(AppointmentStatus.COMPLETED)
        )).thenReturn(updatedApt);

        mockMvc.perform(put("/api/appointment/update/10")
                .param("doctorFiscalCode", "DOC123")
                .param("patientFiscalCode", "PTN123")
                .param("type", "CONSULTATION")
                .param("status", "COMPLETED")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apt)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(10))
                .andExpect(jsonPath("$.appointmentStatus").value("COMPLETED"));
    }
}
