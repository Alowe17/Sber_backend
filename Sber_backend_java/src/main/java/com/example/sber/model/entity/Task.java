package com.example.sber.model.entity;

import com.example.sber.model.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int rewardPoint;
    private int targetValue;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private LocalDate deadline;

    public Task (Long id, String title, String description, int rewardPoint, int targetValue, EventType eventType, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rewardPoint = rewardPoint;
        this.targetValue = targetValue;
        this.eventType = eventType;
        this.deadline = deadline;
    }

    public Task () {}
}