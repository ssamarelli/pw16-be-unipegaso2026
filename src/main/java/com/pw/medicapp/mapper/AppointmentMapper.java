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

    @Mapping(source = "doctor.fiscalCode", target = "doctorFiscalCode")
    @Mapping(source = "doctor.lastName", target = "doctorName")
    @Mapping(source = "patient.fiscalCode", target = "patientFiscalCode")
    @Mapping(source = "patient.lastName", target = "patientName")
    AppointmentDTO toDto(Appointment appointment);

    Appointment toEntity(AppointmentDTO appointmentDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    void updateEntityFromDto(AppointmentDTO dto, @MappingTarget Appointment entity);
}