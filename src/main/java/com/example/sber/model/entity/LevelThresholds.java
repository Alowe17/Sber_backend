package com.example.sber.model.entity;

import com.example.sber.model.enums.EventType;
import jakarta.persistence.*;
import jdk.jfr.Threshold;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.logging.Level;

@Getter
@Setter
@Entity
@Table(name="level_thresholds")
public class LevelThresholds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private Employee employee;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Long value;
    private String sourse_id;
    private LocalDateTime dateTime;

    public LevelThresholds (Long id, Employee employee, EventType eventType, Long value, String sourse_id, LocalDateTime dateTime) {
        this.id = id;
        this.employee = employee;
        this.eventType = eventType;
        this.value = value;
        this.sourse_id = sourse_id;
        this.dateTime = dateTime;
    }

    public LevelThresholds () {}
}
