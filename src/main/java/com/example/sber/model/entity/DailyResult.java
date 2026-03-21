package com.example.sber.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "daily_result")
@Getter
@Setter
public class DailyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    private int dealsCount;
    private int creditVolume;
    private int productCount;
    private LocalDate date;

    public DailyResult (Long id, Employee employee, int dealsCount, int creditVolume, int productCount, LocalDate date) {
        this.id = id;
        this.employee = employee;
        this.dealsCount = dealsCount;
        this.creditVolume = creditVolume;
        this.productCount = productCount;
        this.date = date;
    }

    public DailyResult () {}
}