package com.pw.medicapp.service;

import com.pw.medicapp.exceptionHandler.UserException;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.enums.UserRole;
import com.pw.medicapp.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

//    @Autowired
//    private AppointmentRepository appointmentRepository;

    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        Optional<Doctor> check = doctorRepository.findByFiscalCode(doctor.getFiscalCode());
        if(check.isPresent()) {
            throw new UserException("Doctor already exist");
        }
        doctor.setDoctorId(null);
        doctor.setRole(UserRole.DOCTOR);
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        Optional<Doctor> check = doctorRepository.findByFiscalCode(doctor.getFiscalCode());
        if(check.isEmpty()) {
            throw new UserException("Doctor doesn't exist");
        }
        Doctor existing = check.get();
        if (doctor.getFirstName() != null) existing.setFirstName(doctor.getFirstName());
        if (doctor.getLastName() != null) existing.setLastName(doctor.getLastName());
        if (doctor.getEmail() != null) existing.setEmail(doctor.getEmail());
        if (doctor.getPhoneNumber() != null) existing.setPhoneNumber(doctor.getPhoneNumber());
        if (doctor.getAddress() != null) existing.setAddress(doctor.getAddress());
        if (doctor.getDob() != null) existing.setDob(doctor.getDob());
        if (doctor.getBirthplace() != null) existing.setBirthplace(doctor.getBirthplace());
        if (doctor.getSpecialization() != null) existing.setSpecialization(doctor.getSpecialization());
        if (doctor.getMedicalLicenseNumber() != null) existing.setMedicalLicenseNumber(doctor.getMedicalLicenseNumber());

        return doctorRepository.save(existing);
    }

    @Transactional
    public String deleteDoctor(String fiscalCode) {
        Optional<Doctor> check = doctorRepository.findByFiscalCode(fiscalCode);
        if(check.isEmpty()) {
            throw new UserException("Doctor doesn't exist");
        }
        doctorRepository.deleteByFiscalCode(fiscalCode);
        Optional<Doctor> deleted = doctorRepository.findByFiscalCode(fiscalCode);
        if(deleted.isEmpty()) {
            return "Doctor " +  fiscalCode + " removed correctly";
        } else
        {
            throw new UserException("Doctor has not been removed correctly");
        }
    }

//    public List<PatientDTO> getPatientsByDoctor(String fiscalCode) {
//        // Verifichiamo prima se il dottore esiste
//         User user = docRepository.findByFiscalCode(fiscalCode)
//                .orElseThrow(() -> new RuntimeException("Dottore non trovato con il codice fiscale fornito."));
//
//        // Recuperiamo la lista di entità Patient e la mappiamo in DTO
//        return appointmentRepository.findPatientsByDoctorFiscalCode(fiscalCode)
//                .stream()
//                .map(userMapper::toDto) // Assicurati che il mapper gestisca Patient -> PatientDTO
//                .collect(Collectors.toList());
//    }
//
//
//    public List<AppointmentDTO> getAppointmentsByDoctor(String fiscalCode) {
//        // Verifichiamo se il dottore esiste per dare un errore chiaro in caso contrario
//        User user = docRepository.findByFiscalCode(fiscalCode)
//                .orElseThrow(() -> new RuntimeException("Dottore non trovato con il codice fiscale fornito."));
//
//        // Recuperiamo le entità e le mappiamo in DTO
//        return appointmentRepository.findByDoctorFiscalCode(fiscalCode)
//                .stream()
//                .map(appointmentMapper::toDto)
//                .collect(Collectors.toList());
//    }


}
