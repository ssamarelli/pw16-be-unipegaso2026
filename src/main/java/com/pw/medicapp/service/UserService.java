package com.pw.medicapp.service;

import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.mapper.UserMapper;
import com.pw.medicapp.model.User;
import com.pw.medicapp.model.enums.UserRole;
import com.pw.medicapp.repository.AppointmentRepository;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.repository.PatientRepository;
import com.pw.medicapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    // Recupera tutti gli utenti
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto) // Converte ogni User/Patient/Doctor nel DTO corretto
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user;

        // Determiniamo quale entità specifica creare in base al ruolo
        if (userDTO.getRole() == UserRole.DOCTOR) {
            user = userMapper.toDoctorEntity(userDTO);
        } else if (userDTO.getRole() == UserRole.PATIENT) {
            user = userMapper.toPatientEntity(userDTO);
        } else {
            user = userMapper.toEntity(userDTO);
        }

        user.setUserId(null);

        // Il save gestirà l'inserimento in entrambe le tabelle (users + doctors/patients)
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserDTO updateUser(String fiscalCode, UserDTO userDTO) {
        User existingUser = userRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Aggiorna l'entità esistente con i dati del DTO
        userMapper.updateEntityFromDto(userDTO, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteUser(String fiscalCode) {
        // 1. Recuperiamo l'utente completo dal database
        User user = userRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Impossibile eliminare: utente non trovato"));

        // 2. Cancelliamo gli appuntamenti in base al ruolo
        if (user.getRole() == UserRole.PATIENT) {
            appointmentRepository.deleteByPatient_UserId(user.getUserId());
        } else if (user.getRole() == UserRole.DOCTOR) {
            appointmentRepository.deleteByDoctor_UserId(user.getUserId());
        }

        // 3. Ora che i vincoli di integrità (Foreign Key) sono rispettati, eliminiamo l'utente
        userRepository.delete(user);
    }

    public UserDTO getUserByFiscalCode(String fiscalCode) {
        User user = userRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new EntityNotFoundException("Utente con codice fiscale " + fiscalCode + " non trovato"));

        // Converte l'entità trovata nel DTO specifico (PatientDTO o DoctorDTO)
        return userMapper.toDto(user);
    }
}