package com.example.sber.controller;

import com.example.sber.model.entity.LevelThresholds;
import com.example.sber.model.enums.EventType;
import com.example.sber.repository.LevelThresholdsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/level-thresholds")
@RequiredArgsConstructor
public class LevelThresholdsController {

    private final LevelThresholdsRepository levelThresholdsRepository;

    @GetMapping
    public List<LevelThresholds> getThresholds(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) EventType eventType) {

        if (employeeId != null && eventType != null) {
            return levelThresholdsRepository.findByEmployeeIdAndEventType(employeeId, eventType);
        }
        if (employeeId != null) {
            return levelThresholdsRepository.findByEmployeeId(employeeId);
        }
        if (eventType != null) {
            return levelThresholdsRepository.findByEventType(eventType);
        }
        return levelThresholdsRepository.findAll();
    }
}
