package com.example.sber.repository;

import com.example.sber.model.entity.LevelThresholds;
import com.example.sber.model.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelThresholdsRepository extends JpaRepository<LevelThresholds,Long> {

    List<LevelThresholds> findByEmployeeId(Long employeeId);

    List<LevelThresholds> findByEmployeeIdAndEventType(Long employeeId, EventType eventType);

    List<LevelThresholds> findByEventType(EventType eventType);
}