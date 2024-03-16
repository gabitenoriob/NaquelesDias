package com.example.NaquelesDias.model.HealthCenter;

import java.util.List;

public interface CampaignsRepository {
    List<Campaigns> findByHealthCenterId(int healthCenterId);
}
