package com.example.NaquelesDias.model.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Table(name = "sexual_relation")
@Entity(name = "SexualRelation")
@Getter
@EqualsAndHashCode(of = "id")
public class SexualRelation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat
    private Date periodDate;
    private int user_id;


    public SexualRelation(int id, Date periodDate, int user_id) {
        this.id = id;
        this.periodDate = periodDate;
        this.user_id = user_id;
    }

    public SexualRelation() {
        this.id = id;
        this.periodDate = periodDate;
        this.user_id = user_id;
    }
}
