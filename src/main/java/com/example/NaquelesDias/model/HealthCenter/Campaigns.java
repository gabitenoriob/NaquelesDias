package com.example.NaquelesDias.model.HealthCenter;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "campaigns")
@Entity(name = "Campaigns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Campaigns{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private  int type;
    private  String text;
    private  String attachment;
    private int health_center_id;
}
