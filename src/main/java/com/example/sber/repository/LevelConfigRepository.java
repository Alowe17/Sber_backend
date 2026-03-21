package com.example.sber.repository;

import com.example.sber.model.entity.LevelConfig;
import com.example.sber.model.enums.CurrentLevel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelConfigRepository {

    Optional<LevelConfig> findByCurrentLevel(CurrentLevel currentLevel);
}