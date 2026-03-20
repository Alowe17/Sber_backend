package com.example.sber.model.entity;

import com.example.sber.model.enums.CurrentLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "privilege")
@Setter
@Getter
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int effectMoney;

    @Enumerated(EnumType.STRING)
    private CurrentLevel requiredLevel;

    public Privilege (String name, String description, int effectMoney, CurrentLevel requiredLevel) {
        this.name = name;
        this.description = description;
        this.effectMoney = effectMoney;
        this.requiredLevel = requiredLevel;
    }

    public Privilege() {}
}