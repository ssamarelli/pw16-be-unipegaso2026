package com.pw.medicapp.repository;

import com.pw.medicapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByFiscalCode(String codiceFiscale);

    void deleteByFiscalCode(String fiscalCode);

    Optional<Doctor> getDoctorByFiscalCode(String fiscalCode);
    List<Doctor> fiscalCode(String fiscalCode);
}
