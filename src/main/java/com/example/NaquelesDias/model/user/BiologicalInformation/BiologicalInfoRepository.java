package com.example.NaquelesDias.model.user.BiologicalInformation;

import com.example.NaquelesDias.model.user.BiologicalInformation.BiologicalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiologicalInfoRepository extends JpaRepository<BiologicalInformation, String> {
}