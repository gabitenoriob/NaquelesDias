package com.example.NaquelesDias.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Blob;
import java.util.Date;

@Table(name = "period")
@Entity(name = "Period")
@Getter
@EqualsAndHashCode(of = "id")
public class Period {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat
    private Date periodDate;
    private int user_id;

    public Period (Date periodDate, int user_id) {
        this.periodDate = periodDate;
        this.user_id = user_id;
    }

    public Period() {
        this.periodDate = periodDate;
        this.user_id = user_id;
    }
}
