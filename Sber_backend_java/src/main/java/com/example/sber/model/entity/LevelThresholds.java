package com.example.sber.model.entity;

import com.example.sber.model.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="level_thresholds")
public class LevelThresholds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Long value;
    private String sourceId;
    private LocalDateTime dateTime;

    public LevelThresholds (Long id, Employee employee, EventType eventType, Long value, String sourceId, LocalDateTime dateTime) {
        this.id = id;
        this.employee = employee;
        this.eventType = eventType;
        this.value = value;
        this.sourceId = sourceId;
        this.dateTime = dateTime;
    }

    public LevelThresholds () {}
}
