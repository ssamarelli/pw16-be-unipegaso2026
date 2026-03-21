package com.pw.medicapp.repository;

import com.pw.medicapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Modifying
    @Transactional
    void deleteByPatient_UserId(Integer userId);

    @Modifying
    @Transactional
    void deleteByDoctor_UserId(Integer userId);




}
