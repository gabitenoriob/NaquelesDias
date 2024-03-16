package com.example.NaquelesDias.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "pregnancy")
@Entity(name = "Pregnancy")
@Getter
@EqualsAndHashCode(of = "id")
public class Pregnancy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean pregnant;
    private int weeks;
    private int user_id;

    public Pregnancy(){
        this.id = id;
        this.user_id = user_id;
        this.pregnant = false;
        this.weeks = weeks;

    }

    public Pregnancy(int id, boolean pregnant, int weeks, int user_id) {
        this.id = id;
        this.user_id = user_id;
        this.pregnant = pregnant;
        this.weeks = weeks;
    }
}
