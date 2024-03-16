package com.example.NaquelesDias.model.HealthCenter;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository {
    List<Doctor> findByHealthCenterId(int healthCenterId);

    Doctor save(Doctor doctor);
}
