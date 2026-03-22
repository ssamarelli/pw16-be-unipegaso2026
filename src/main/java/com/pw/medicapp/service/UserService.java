package com.pw.medicapp.service;

import com.pw.medicapp.DTO.DoctorDTO;
import com.pw.medicapp.DTO.PatientDTO;
import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.mapper.UserMapper;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
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
    public List<UserDTO> getAllUsers(UserRole role) {
        List<User> users;

        if (role != null) {
            // Filtriamo per ruolo se specificato
            users = userRepository.findByRole(role);
        } else {
            // Altrimenti recuperiamo tutti gli utenti
            users = userRepository.findAll();
        }

        return users.stream()
                .map(userMapper::toDto)
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

        // Se l'oggetto arrivato è un PatientDTO e l'entità è un Patient
        if (userDTO instanceof PatientDTO patientDTO && existingUser instanceof Patient patient) {
            userMapper.updatePatientFromDto(patientDTO, patient);
        }
        // Se l'oggetto arrivato è un DoctorDTO e l'entità è un Doctor
        else if (userDTO instanceof DoctorDTO doctorDTO && existingUser instanceof Doctor doctor) {
            userMapper.updateDoctorFromDto(doctorDTO, doctor);
        }
        // Altrimenti aggiornamento generico User
        else {
            userMapper.updateEntityFromDto(userDTO, existingUser);
        }

        // Hibernate rileva le modifiche sugli oggetti 'patient' o 'doctor'
        // perché sono reference dell'oggetto 'existingUser'
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