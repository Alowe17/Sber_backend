package com.example.sber.controller;

import com.example.sber.model.entity.LevelConfig;
import com.example.sber.model.enums.CurrentLevel;
import com.example.sber.repository.LevelConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level-config")
@RequiredArgsConstructor
public class LevelConfigController {

    private final LevelConfigRepository levelConfigRepository;

    @GetMapping
    public List<LevelConfig> getLevelConfig() {
        return levelConfigRepository.findAll();
    }

    @GetMapping("/by-level")
    public LevelConfig getByLevel(@RequestParam CurrentLevel currentLevel) {
        return levelConfigRepository.findByCurrentLevel(currentLevel).orElseThrow();
    }
}
