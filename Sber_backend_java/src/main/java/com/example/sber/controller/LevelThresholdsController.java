package com.example.sber.controller;

import com.example.sber.model.entity.LevelThresholds;
import com.example.sber.model.enums.EventType;
import com.example.sber.service.EmployeeService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level-thresholds")
@RequiredArgsConstructor
@Validated
public class LevelThresholdsController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<LevelThresholds> getThresholds(
            @RequestParam(required = false) @Positive Long employeeId,
            @RequestParam(required = false) EventType eventType) {
        return employeeService.getThresholds(employeeId, eventType);
    }
}
