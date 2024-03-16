package com.example.NaquelesDias.model.HealthCenter;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CampaignsRepository {
    List<Campaigns> findByHealthCenterId(int healthCenterId);

    Campaigns save(Campaigns campaign);
}
