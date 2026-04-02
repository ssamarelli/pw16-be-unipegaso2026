package com.pw.medicapp.service;

import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.User;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Recupera tutti gli utenti
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        List<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor : doctors) {
            User user = fromDoctorToUser(doctor);
            users.add(user);
        }
        List<Patient> patients = patientRepository.findAll();
        for (Patient patient : patients) {
            User user = fromPatientToUser(patient);
            users.add(user);
        }
        return users;
    }


    public User fromDoctorToUser(Doctor doctor) {
        User user = new User();
        user.setRole(doctor.getRole());
        user.setFirstName(doctor.getFirstName());
        user.setLastName(doctor.getLastName());
        user.setEmail(doctor.getEmail());
        user.setPhoneNumber(doctor.getPhoneNumber());
        user.setFiscalCode(doctor.getFiscalCode());
        return user;
    }

    public User fromPatientToUser(Patient patient) {
        User user = new User();
        user.setRole(patient.getRole());
        user.setFirstName(patient.getFirstName());
        user.setLastName(patient.getLastName());
        user.setEmail(patient.getEmail());
        user.setPhoneNumber(patient.getPhoneNumber());
        user.setFiscalCode(patient.getFiscalCode());
        return user;
    }
}