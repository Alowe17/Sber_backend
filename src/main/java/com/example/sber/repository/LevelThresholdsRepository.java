package com.example.sber.repository;

import com.example.sber.model.entity.LevelThresholds;
import com.example.sber.model.enums.EventType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelThresholdsRepository {

    List<LevelThresholds> findByEmployeeId(Long employeeId);

    List<LevelThresholds> findByEmployeeIdAndEventType(Long employeeId, EventType eventType);

    List<LevelThresholds> findByEventType(EventType eventType);
}