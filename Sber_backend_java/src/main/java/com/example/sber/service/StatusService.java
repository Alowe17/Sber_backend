package com.example.sber.service;

import com.example.sber.model.dto.response.StatusDto;
import com.example.sber.model.entity.Employee;
import com.example.sber.model.entity.LevelConfig;
import com.example.sber.repository.EmployeeRepository;
import com.example.sber.repository.LevelConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    private final EmployeeRepository employeeRepository;
    private final LevelConfigRepository levelConfigRepository;

    public StatusService (EmployeeRepository employeeRepository, LevelConfigRepository levelConfigRepository) {
        this.employeeRepository = employeeRepository;
        this.levelConfigRepository = levelConfigRepository;
    }

    public StatusDto getStatus (Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        List<LevelConfig> levelConfigList = levelConfigRepository.findAll();

        LevelConfig currentConfig = null;
        LevelConfig nextConfig = null;

        for (LevelConfig levelConfig : levelConfigList) {
            if (levelConfig.getCurrentLevel() == employee.getCurrentLevel()) {
                currentConfig = levelConfig;
            }

            if (levelConfig.getMinPoints() > employee.getRatingPoints()) {
                if (nextConfig == null || levelConfig.getMinPoints() < nextConfig.getMinPoints()) {
                    nextConfig = levelConfig;
                }
            }
        }

        int pointToNext = 0;
        int progress = 0;

        if (nextConfig != null && currentConfig != null) {
            pointToNext = nextConfig.getMinPoints() - employee.getRatingPoints();

            int range = nextConfig.getMinPoints() - currentConfig.getMinPoints();
            int passed = employee.getRatingPoints() - currentConfig.getMinPoints();

            if (range > 0) {
                progress = (int) ((passed * 100.00) / range);
            }
        }

        StatusDto statusDto = new StatusDto();
        statusDto.setCurrentLevel(employee.getCurrentLevel());
        statusDto.setCurrentPoints(employee.getRatingPoints());
        statusDto.setProgressPercent(progress);
        statusDto.setPointsToNext(pointToNext);
        statusDto.setNextLevel(employee.getCurrentLevel() != null ? nextConfig.getCurrentLevel() : employee.getCurrentLevel());
        return statusDto;
    }
}