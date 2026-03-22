package com.pw.medicapp.mapper;

import com.pw.medicapp.DTO.AppointmentDTO;
import com.pw.medicapp.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "doctor.userId", target = "doctorId")
    @Mapping(source = "doctor.lastName", target = "doctorName")
    @Mapping(source = "patient.userId", target = "patientId")
    @Mapping(source = "patient.lastName", target = "patientName")
    AppointmentDTO toDto(Appointment appointment);

    Appointment toEntity(AppointmentDTO appointmentDTO);
}