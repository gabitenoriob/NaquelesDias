package com.example.NaquelesDias.model.HealthCenter;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> findByHealthCenterId(int healthCenterId);
}
