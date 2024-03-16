package com.example.NaquelesDias.model.user.BiologicalInformation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Table(name = "birth_control")
@Entity(name = "BirthControl")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BirthControl {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat
    private Date date;
    private int user_id;
    private boolean use;
}
