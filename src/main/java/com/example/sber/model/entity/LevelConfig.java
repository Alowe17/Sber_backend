package com.example.sber.model.entity;

import com.example.sber.model.enums.CurrentLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "level_config")
public class LevelConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CurrentLevel currentLevel;

    private int minPoints;

    public LevelConfig (Long id, CurrentLevel currentLevel, int minPoints) {
        this.id = id;
        this.currentLevel = currentLevel;
        this.minPoints = minPoints;
    }

    public LevelConfig() {}
}