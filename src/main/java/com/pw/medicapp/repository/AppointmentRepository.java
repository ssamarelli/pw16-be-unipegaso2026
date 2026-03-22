package com.pw.medicapp.repository;

import com.pw.medicapp.model.Appointment;
import com.pw.medicapp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Modifying
    @Transactional
    void deleteByPatient_UserId(Integer userId);

    @Modifying
    @Transactional
    void deleteByDoctor_UserId(Integer userId);

    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor.fiscalCode = :fiscalCode")
    List<Patient> findPatientsByDoctorFiscalCode(@Param("fiscalCode") String fiscalCode);

    // Recupera tutti gli appuntamenti filtrando per il codice fiscale del dottore associato
    List<Appointment> findByDoctorFiscalCode(String fiscalCode);



}
