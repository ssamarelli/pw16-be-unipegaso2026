package com.pw.medicapp.mapper;

import com.pw.medicapp.DTO.DoctorDTO;
import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring") // "spring" permette di fare @Autowired del mapper
public interface UserMapper {

    // Mappatura generica
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);

    // Mappatura specifica per Patient
    PatientDTO toDto(Patient patient);
    Patient toEntity(PatientDTO patientDTO);

    DoctorDTO toDto(Doctor doctor);
    Doctor toEntity(DoctorDTO doctorDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userId", ignore = true)
    void updateEntityFromDto(UserDTO userDTO, @MappingTarget User existingUser);
}