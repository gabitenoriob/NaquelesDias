package com.example.NaquelesDias.service;

import com.example.NaquelesDias.infrastructure.DistanceCalculator;
import com.example.NaquelesDias.model.user.AddressInformation;
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
import java.util.LinkedHashMap;

@Service
public class HealthCenterService {

    private final Logger logger = LoggerFactory.getLogger(BloodCenterService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DistanceCalculator distanceCalculator;
    @Autowired
    private Gson gson = new Gson();

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
            String healthCenterCep =  String.valueOf(healthCenter.get("cep"));

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

    private String convertListToJson(List<Map<String, Object>> list) {
        return gson.toJson(list);
    }

    /*public String getBloodCenterStock(int bloodCenterId) {
        logger.info("-Starting BloodCenterStock GET-");

        String sql = "SELECT" +
                "       SUM(CASE WHEN bs.type = 'A+' THEN bs.quantity ELSE 0 END)," +
                "       SUM(CASE WHEN bs.type = 'B+' THEN bs.quantity ELSE 0 END)," +
                "       SUM(CASE WHEN bs.type = 'AB+' THEN bs.quantity ELSE 0 END)," +
                "       SUM(CASE WHEN bs.type = 'O+' THEN bs.quantity ELSE 0 END)," +
                "       SUM(CASE WHEN bs.type = 'A-' THEN bs.quantity ELSE 0 END)," +
                "       SUM(CASE WHEN bs.type = 'B-' THEN bs.quantity ELSE 0 END)," +
                "       SUM(CASE WHEN bs.type = 'AB-' THEN bs.quantity ELSE 0 END)," +
                "       SUM(CASE WHEN bs.type = 'O-' THEN bs.quantity ELSE 0 END)" +
                "FROM blood_stock bs " +
                "INNER JOIN blood_center bc ON bs.blood_center_id = bc.id " +
                "WHERE bs.blood_center_id = " + bloodCenterId;

        List<Map<String, Object>> bloodCenterStock = jdbcTemplate.queryForList(sql);

        String result = convertStockListToJson(bloodCenterStock);

        return Objects.requireNonNullElse(result, "{}");
    }

    private String convertStockListToJson(List<Map<String, Object>> list) {
        Map<String, Double> resultMap = new LinkedHashMap<>();

        for (Map<String, Object> entry : list) {
            entry.forEach((key, value) -> {
                String bloodType = key.replace("SUM(CASE WHEN bs.type = '", "")
                        .replace("' THEN bs.quantity ELSE 0 END)", "");
                resultMap.put(bloodType, (Double) value);
            });
        }

        return gson.toJson(resultMap);
    }*/
}