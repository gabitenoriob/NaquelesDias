package com.example.NaquelesDias.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "pregnancy")
@Entity(name = "Pregnancy")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pregnancy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean pregnant;
    private int weeks;
    private int user_id;

    public Pregnancy(int id, int weeks, int user_id){
        this.id = id;
        this.pregnant = false;
        this.weeks = weeks;
        this.user_id = user_id;
    }


}
