package com.example.NaquelesDias.model.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Table(name = "health_needs")
@Entity(name = "HealthNeeds")
@Getter
@EqualsAndHashCode(of = "id")
public class HealthNeeds {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat
    private Date periodDate;
    private int user_id;
    private  int mensruation_cicle;


    public HealthNeeds(int id, Date periodDate, int user_id, int mensruation_cicle) {
        this.id = id;
        this.periodDate = periodDate;
        this.user_id = user_id;
        this.mensruation_cicle = mensruation_cicle;
    }

    public HealthNeeds() {
        this.id = id;
        this.periodDate = periodDate;
        this.user_id = user_id;
        this.mensruation_cicle = mensruation_cicle;
    }
}
