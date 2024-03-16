package com.example.NaquelesDias.service;

import com.example.NaquelesDias.infrastructure.DistanceCalculator;
import com.example.NaquelesDias.model.HealthCenter.Campaigns;
import com.example.NaquelesDias.model.HealthCenter.CampaignsRepository;
import com.example.NaquelesDias.model.HealthCenter.Doctor;
import com.example.NaquelesDias.model.HealthCenter.DoctorRepository;
import com.example.NaquelesDias.model.user.BiologicalInformation.AddressInformation;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class HealthCenterService {

    private final Logger logger = LoggerFactory.getLogger(BloodCenterService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DistanceCalculator distanceCalculator;
    @Autowired
    private Gson gson = new Gson();

    private DoctorRepository doctorRepository;
    private CampaignsRepository campaignsRepository;

    public String getHealthCenterList(AddressInformation userAddressInfo) {
        logger.info("-Starting HealthCenterList GET-");

        String sql = "SELECT hc.id, hc.name, ai.street, ai.number, ai.city, ai.state, ai.cep FROM health_center hc " +
                "INNER JOIN address_information ai ON hc.address_info_id = ai.id";

        List<Map<String, Object>> healthCenters = jdbcTemplate.queryForList(sql);

        List<Map<String, Object>> healthCentersWithDistance = new ArrayList<>();
        for (Map<String, Object> healthCenter : healthCenters) {
            logger.info("HealthCenter: {}", healthCenter);

            String healthCenterStreet = String.valueOf(healthCenter.get("street"));
            String healthCenterNumber = String.valueOf(healthCenter.get("number"));
            String healthCenterCity = String.valueOf(healthCenter.get("city"));
            String healthCenterState = String.valueOf(healthCenter.get("state"));
            String healthCenterCep = String.valueOf(healthCenter.get("cep"));

            AddressInformation healthCentersAddressInfo = new AddressInformation(Integer.parseInt(healthCenterCep), healthCenterStreet,
                    Integer.parseInt(healthCenterNumber), healthCenterCity, healthCenterState);

            double distance = distanceCalculator.getDistance(userAddressInfo, healthCentersAddressInfo);
            healthCenter.put("distance", distance);
            healthCentersWithDistance.add(healthCenter);
        }

        String result = convertListToJson(healthCentersWithDistance);

        return Objects.requireNonNullElse(result, "{}");
    }

    public String getDetailedHealthCenterList() {
        logger.info("-Starting DetailedHealthCenterList GET-");

        String sql = "SELECT hc.id, hc.name, " +
                "ai.street, ai.number, ai.city, ai.state, " +
                "hc.operating_time, hc.phone_number " +
                "FROM health_center hc " +
                "INNER JOIN address_information ai ON hc.address_info_id = ai.id";

        List<Map<String, Object>> healthCenters = jdbcTemplate.queryForList(sql);

        String result = convertListToJson(healthCenters);

        return Objects.requireNonNullElse(result, "{}");
    }

    public String getDoctorsHealthCenterList(int healthCenterId) {
        List<Doctor> doctors = doctorRepository.findByHealthCenterId(healthCenterId);
        String result = gson.toJson(doctors);
        return Objects.requireNonNullElse(result, "[]");
    }
    private String convertListToJson(List<Map<String, Object>> list) {
        return gson.toJson(list);
    }

    public String getCampaignsHealthCenterList(int healthCenterId) {
        List<Campaigns> campaigns = campaignsRepository.findByHealthCenterId(healthCenterId);
        String result = gson.toJson(campaigns);
        return Objects.requireNonNullElse(result, "[]");
    }
}