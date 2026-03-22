package com.pw.medicapp.service;

import com.pw.medicapp.DTO.AppointmentDTO;
import com.pw.medicapp.DTO.DoctorDTO;
import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.mapper.AppointmentMapper;
import com.pw.medicapp.mapper.UserMapper;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.User;
import com.pw.medicapp.repository.AppointmentRepository;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.repository.PatientRepository;
import com.pw.medicapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;


    public List<PatientDTO> getPatientsByDoctor(String fiscalCode) {
        // Verifichiamo prima se il dottore esiste
         User user = userRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Dottore non trovato con il codice fiscale fornito."));

        // Recuperiamo la lista di entità Patient e la mappiamo in DTO
        return appointmentRepository.findPatientsByDoctorFiscalCode(fiscalCode)
                .stream()
                .map(userMapper::toDto) // Assicurati che il mapper gestisca Patient -> PatientDTO
                .collect(Collectors.toList());
    }


    public List<AppointmentDTO> getAppointmentsByDoctor(String fiscalCode) {
        // Verifichiamo se il dottore esiste per dare un errore chiaro in caso contrario
        User user = userRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Dottore non trovato con il codice fiscale fornito."));

        // Recuperiamo le entità e le mappiamo in DTO
        return appointmentRepository.findByDoctorFiscalCode(fiscalCode)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
