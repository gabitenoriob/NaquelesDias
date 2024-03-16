package com.example.NaquelesDias.model.user.BiologicalInformation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Table(name = "sexual_relation")
@Entity(name = "SexualRelation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SexualRelation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat
    private Date date;
    private int user_id;
}
