package com.example.sber.service;

import com.example.sber.model.dto.response.RatingDetailDto;
import com.example.sber.model.entity.Employee;
import com.example.sber.model.entity.LevelThresholds;
import com.example.sber.model.enums.EventType;
import com.example.sber.repository.EmployeeRepository;
import com.example.sber.repository.LevelThresholdsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {

    private final EmployeeRepository employeeRepository;
    private final LevelThresholdsRepository thresholdsRepository;

    public RatingService(EmployeeRepository employeeRepository, LevelThresholdsRepository thresholdsRepository) {
        this.employeeRepository = employeeRepository;
        this.thresholdsRepository = thresholdsRepository;
    }

    public void updateRating(Employee employee, int deals, double volume, int products) {

        int dealPoints = deals * 2;
        int volumePoints = (int) volume * 3;
        int productPoints = products;
        int total = dealPoints + volumePoints + productPoints;
        employee.setRatingPoints(employee.getRatingPoints() + total);
        employeeRepository.save(employee);
        saveThreshold(employee, EventType.NEW_DEAL, dealPoints);
        saveThreshold(employee, EventType.MARKET_SHARE, volumePoints);
        saveThreshold(employee, EventType.BONUS, productPoints);
    }

    private void saveThreshold(Employee employee, EventType type, long value) {
        if (value == 0) return;

        LevelThresholds levelThresholds = new LevelThresholds();
        levelThresholds.setEmployee(employee);
        levelThresholds.setEventType(type);
        levelThresholds.setValue(value);
        levelThresholds.setDateTime(LocalDateTime.now());
        thresholdsRepository.save(levelThresholds);
    }

    public List<RatingDetailDto> getDetails(Long employeeId) {

        List<LevelThresholds> list = thresholdsRepository.findByEmployeeId(employeeId);

        Map<EventType, Long> map = new HashMap<>();

        for (LevelThresholds l : list) {
            map.put(
                    l.getEventType(),
                    map.getOrDefault(l.getEventType(), 0L) + l.getValue()
            );
        }

        List<RatingDetailDto> result = new ArrayList<>();

        for (Map.Entry<EventType, Long> entry : map.entrySet()) {
            RatingDetailDto dto = new RatingDetailDto();
            dto.setType(entry.getKey());
            dto.setPoints(entry.getValue());
            result.add(dto);
        }

        return result;
    }
}