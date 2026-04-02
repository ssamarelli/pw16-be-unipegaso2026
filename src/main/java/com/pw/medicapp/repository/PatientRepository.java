package com.pw.medicapp.repository;

import com.pw.medicapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByFiscalCode(String fiscalCode);

    void deleteByFiscalCode(String fiscalCode);

    List<Patient> getPatientByFiscalCode(String fiscalCode);

    List<Patient> findByFiscalCodeIn(List<String> patientFiscalCodes);

    boolean existsByFiscalCode(String fiscalCode);

}
