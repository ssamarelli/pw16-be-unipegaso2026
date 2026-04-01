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

    // Da ENTITY a DTO (quando restituisci i dati al frontend)
    @Mapping(source = "doctor.fiscalCode", target = "doctorFiscalCode")
    @Mapping(target = "doctorName", expression = "java(\"Dott. \" + appointment.getDoctor().getLastName() + \" \" + appointment.getDoctor().getFirstName())")
    @Mapping(source = "patient.fiscalCode", target = "patientFiscalCode")
    @Mapping(target = "patientName", expression = "java(appointment.getPatient().getFirstName() + \" \" + appointment.getPatient().getLastName())")
    @Mapping(source = "appointmentDate", target = "date")
    @Mapping(source = "appointmentTime", target = "time")
    AppointmentDTO toDto(Appointment appointment);

    // Da DTO a ENTITY (quando crei un nuovo appuntamento)
    @Mapping(source = "date", target = "appointmentDate")
    @Mapping(source = "time", target = "appointmentTime")
    @Mapping(target = "patient", ignore = true)        // Gestito nel Service tramite CF
    @Mapping(target = "doctor", ignore = true)         // Gestito nel Service tramite CF
    @Mapping(target = "appointmentId", ignore = true)  // Autogenerato dal DB
    @Mapping(target = "appointmentStatus", ignore = true) // Gestito nel Service/Controller
    @Mapping(target = "appointmentType", ignore = true)   // Gestito nel Service/Controller
    Appointment toEntity(AppointmentDTO appointmentDTO);

    // UPDATE: Da DTO a ENTITY esistente
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(source = "date", target = "appointmentDate")
    @Mapping(source = "time", target = "appointmentTime")
    void updateEntityFromDto(AppointmentDTO dto, @MappingTarget Appointment entity);
}