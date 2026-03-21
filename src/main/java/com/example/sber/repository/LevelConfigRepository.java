package com.example.sber.repository;

import com.example.sber.model.entity.LevelConfig;
import com.example.sber.model.enums.CurrentLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LevelConfigRepository extends JpaRepository<LevelConfig, Long> {

    List<LevelConfig> findAll();
    Optional<LevelConfig> findByCurrentLevel(CurrentLevel currentLevel);
}