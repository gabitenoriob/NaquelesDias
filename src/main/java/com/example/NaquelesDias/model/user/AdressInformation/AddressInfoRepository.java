package com.example.NaquelesDias.model.user.AdressInformation;

import com.example.NaquelesDias.model.user.BiologicalInformation.AddressInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressInfoRepository extends JpaRepository<AddressInformation, String> {

    AddressInformation findById(int id);
}
