package com.pw.medicapp.mapper;

import com.pw.medicapp.DTO.AppointmentDTO;
import com.pw.medicapp.model.Appointment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "doctor.userId", target = "doctorId")
    @Mapping(source = "doctor.lastName", target = "doctorName")
    @Mapping(source = "patient.userId", target = "patientId")
    @Mapping(source = "patient.lastName", target = "patientName")
    @Mapping(source = "appointmentDate", target = "date")        // ← entity → DTO
    @Mapping(source = "appointmentTime", target = "time")
    AppointmentDTO toDto(Appointment appointment);

    @Mapping(source = "date", target = "appointmentDate")        // ← DTO → entity
    @Mapping(source = "time", target = "appointmentTime")
    @Mapping(target = "patient", ignore = true)                  // gestito nel service
    @Mapping(target = "doctor", ignore = true)                   // gestito nel service
    @Mapping(target = "appointmentId", ignore = true)            // mai dal client
    Appointment toEntity(AppointmentDTO appointmentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(source = "date", target = "appointmentDate")
    @Mapping(source = "time", target = "appointmentTime")
    void updateEntityFromDto(AppointmentDTO dto, @MappingTarget Appointment entity);
}