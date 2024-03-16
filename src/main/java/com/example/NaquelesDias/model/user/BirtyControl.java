package com.example.NaquelesDias.model.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Table(name = "birty_control")
@Entity(name = "BirtyControl")
@Getter
@EqualsAndHashCode(of = "id")
public class BirtyControl {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat
    private Date periodDate;
    private int user_id;
    private boolean use;


    public BirtyControl(int id, Date periodDate, int user_id, boolean use) {
        this.id = id;
        this.periodDate = periodDate;
        this.user_id = user_id;
        this.use = use;
    }

    public BirtyControl() {
        this.id = id;
        this.periodDate = periodDate;
        this.user_id = user_id;
        this.use = use;
    }
}
