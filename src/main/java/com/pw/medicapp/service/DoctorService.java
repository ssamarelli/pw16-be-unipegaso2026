package com.pw.medicapp.service;

import com.pw.medicapp.exceptionHandler.UserException;
import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.Doctor;
import com.pw.medicapp.model.Patient;
import com.pw.medicapp.model.enums.UserRole;
import com.pw.medicapp.repository.AppointmentRepository;
import com.pw.medicapp.repository.DoctorRepository;
import com.pw.medicapp.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Doctor getDoctorByFiscalCode(String fiscalCode) {
        // Recuperiamo l'Optional dal repository e lo "spacchettiamo"
        return doctorRepository.getDoctorByFiscalCode(fiscalCode)
                .orElseThrow(() -> new RuntimeException("Dottore non trovato con codice fiscale: " + fiscalCode));
    }

    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        Optional<Doctor> check = doctorRepository.findByFiscalCode(doctor.getFiscalCode());
        if(check.isPresent()) {
            throw new UserException("Il codice fiscale è già assegnato ad un dottore");
        }
        doctor.setDoctorId(null);
        doctor.setRole(UserRole.DOCTOR);
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        Optional<Doctor> check = doctorRepository.findByFiscalCode(doctor.getFiscalCode());
        if(check.isEmpty()) {
            throw new UserException("Non esiste un dottore con questo codice fiscale");
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
            throw new UserException("Non esiste un dottore con questo codice fiscale");
        }
        doctorRepository.deleteByFiscalCode(fiscalCode);
        Optional<Doctor> deleted = doctorRepository.findByFiscalCode(fiscalCode);
        if(deleted.isEmpty()) {
            return "Il dottore con codice fiscale  " +  fiscalCode + " è stato rimosso correttamente";
        } else
        {
            throw new UserException("Errore nella rimozione del dottore");
        }
    }

    public List<Patient> getPatientsByDoctor(String fiscalCode) {
        Doctor doctor = doctorRepository.findByFiscalCode(fiscalCode).orElse(null);
        if (doctor == null) {
            throw new UserException("Dottore non trovato.");
        }

        List<Appointment> appointments = appointmentRepository.findByDoctorFiscalCode(fiscalCode);
        List<String> patientFiscalCodes = new ArrayList<>();
        for (Appointment app : appointments) {
            String cf = app.getPatientFiscalCode();
            if (!patientFiscalCodes.contains(cf)) {
                patientFiscalCodes.add(cf);
            }
        }
        return patientRepository.findByFiscalCodeIn(patientFiscalCodes);
    }

    public List<Appointment> getAppointmentsByDoctor(String fiscalCode) {
        Doctor doctor = doctorRepository.findByFiscalCode(fiscalCode).orElse(null);
        if (doctor == null) {
            throw new UserException("Dottore non trovato.");
        }
        return appointmentRepository.findByDoctorFiscalCode(fiscalCode);
    }

}
