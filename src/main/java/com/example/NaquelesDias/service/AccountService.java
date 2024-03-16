package com.example.NaquelesDias.service;

import com.example.NaquelesDias.model.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Date;

import static org.apache.logging.log4j.util.Strings.concat;

@Service
public class AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Gson gson = new Gson();

    @Autowired
    UserRepository userRepository;

    public String getAccountInfoList(int userId) {
        String sql = "SELECT U.firstname, U.lastname, U.email, U.levelOfEducation, " +
                "BI.birthday, BI.biological_sex, BI.weight , BI.gender" + "AI.street, AI.number, AI.city, AI.state"+
                "FROM user U " +
                "INNER JOIN biological_information BI ON U.biological_info_id = BI.id " +
                "INNER JOIN address_information AI ON U.address_info_id = AI.id " +
                "WHERE U.id = " + userId;

        Map<String, Object> accountInfo = jdbcTemplate.queryForObject(sql, new AccountInfoRowMapper());
        String result = convertMapToJson(accountInfo);
        return Objects.requireNonNullElse(result, "{}");
    }

    public String getHealthInfoBooleans(int userId) throws JsonProcessingException {
        boolean isPregnant = userRepository.findStartOfCurrentPregnancy(userId) != null;
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(isPregnant);

        return Objects.requireNonNullElse(result, "{}");
    }

    private String convertMapToJson(Map<String, Object> map) {
        return gson.toJson(map);
    }
}

@Service
class AccountInfoRowMapper implements RowMapper<Map<String, Object>> {
    @Override
    public Map<String, Object> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Map<String, Object> accountInfo = new LinkedHashMap<>();

        String firstname = concat(resultSet.getString("firstname"), " ");
        String name = concat(firstname, resultSet.getString("lastname"));

        accountInfo.put("name", name);
        accountInfo.put("email", resultSet.getString("email"));
        accountInfo.put("birthday", resultSet.getString("birthday"));
        accountInfo.put("level_of_education",resultSet.getString("level_of_education"));
        accountInfo.put("biological_sex", resultSet.getString("biological_sex"));
        accountInfo.put("gender",resultSet.getString("gender"));
        accountInfo.put("weight", resultSet.getString("weight"));
        accountInfo.put("street",resultSet.getString("street"));
        accountInfo.put("number",resultSet.getString("number"));
        accountInfo.put("city",resultSet.getString("city"));
        accountInfo.put("state",resultSet.getString("state"));

        return accountInfo;
    }
}