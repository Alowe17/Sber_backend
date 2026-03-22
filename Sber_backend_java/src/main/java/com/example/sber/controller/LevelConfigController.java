package com.example.sber.controller;

import com.example.sber.model.entity.LevelConfig;
import com.example.sber.model.enums.CurrentLevel;
import com.example.sber.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level-config")
@RequiredArgsConstructor
public class LevelConfigController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<LevelConfig> getLevelConfig() {
        return employeeService.getLevelConfig();
    }

    @GetMapping("/by-level")
    public LevelConfig getByLevel(@RequestParam CurrentLevel currentLevel) {
        return employeeService.getLevelConfigByLevel(currentLevel);
    }
}
