package com.example.NaquelesDias.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
    void setPassword(@Param("userId") int userId, @Param("newPassword") String newPassword);

    @Query("SELECT password FROM User u WHERE u.id = :userId")
    String getPassword(@Param("userId") int userId);

    @Query("SELECT bi.biologicalSex FROM BiologicalInformation bi " +
            "INNER JOIN User u ON u.biologicalInfoId = bi.id " +
            "WHERE u.id = :userId")
    String getBiologicalSex(@Param("userId") int userId);

    @Query("SELECT bi.gender FROM BiologicalInformation bi " +
        "INNER JOIN User u ON u.biologicalInfoId = bi.id " +
        "WHERE u.id = :userId")
    String getGender(@Param("userId") int userId);

    @Query("INSERT date IN Period p WHERE p.user_id = :userId")
    Date setPeriodDate(@Param("userId") int userId, @Param("date") Date date);

    @Query("SELECT MAX(p.date) FROM Period p WHERE p.user_id = :userId")
    Date findLastPeriod(@Param("userId") int userId);

    @Query("SELECT DATE_ADD(MAX(p.date), INTERVAL 1 MONTH) FROM Period p WHERE p.user_id = :userId")
    Date findNextPeriod(@Param("userId") int userId);

    @Query("INSERT date IN BirthControl bc WHERE bc.user_id = :userId")
    Date setBirthControlDate(@Param("userId") int userId, @Param("date") Date date);

    @Query("SELECT MAX(bc.date) FROM BirthControl bc WHERE bc.user_id = :userId")
    Date findLastBirthControl(@Param("userId") int userId);

    @Query("SELECT DATE_ADD(MAX(bc.date), INTERVAL 1 MONTH) FROM BirthControl bc WHERE bc.user_id = :userId")
    Date findNextBirthControl(@Param("userId") int userId);

    @Query("INSERT date IN SexualRelation sr WHERE sr.user_id = :userId")
    Date setSexualRelationDate(@Param("userId") int userId,  @Param("date") Date date);

    @Query("SELECT MAX(sr.date) FROM SexualRelation sr WHERE sr.user_id = :userId")
    Date findLastSexualRelation(@Param("userId") int userId);

    @Query("INSERT start_date IN SexualRelation sr WHERE sr.user_id = :userId")
    Date setPregnancy(@Param("userId") int userId, @Param("start_date") Date start_date);

    @Query("SELECT MAX(p.date) FROM Pregnancy p WHERE p.user_id = :userId")
    Date findStartOfCurrentPregnancy(@Param("userId") int userId);
}