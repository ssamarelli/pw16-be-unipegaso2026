package com.pw.medicapp.service;

import com.pw.medicapp.DTO.UserDTO;
import com.pw.medicapp.mapper.UserMapper;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.User;
import com.pw.medicapp.model.enums.UserRole;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.repository.PatientRepository;
import com.pw.medicapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

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

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(0);

        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserDTO updateUser(int userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Aggiorna l'entità esistente con i dati del DTO
        userMapper.updateEntityFromDto(userDTO, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Impossibile eliminare: utente non trovato");
        }
        userRepository.deleteById(userId);
    }

    public UserDTO getUserByFiscalCode(String fiscalCode) {
        User user = userRepository.findByFiscalCode(fiscalCode)
                .orElseThrow(() -> new EntityNotFoundException("Utente con codice fiscale " + fiscalCode + " non trovato"));

        // Converte l'entità trovata nel DTO specifico (PatientDTO o DoctorDTO)
        return userMapper.toDto(user);
    }
}