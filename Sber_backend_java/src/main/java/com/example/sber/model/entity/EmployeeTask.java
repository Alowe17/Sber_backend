package com.example.sber.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee_task")
@Getter
@Setter
public class EmployeeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private int progress;
    private boolean completed;

    public EmployeeTask (Long id, Employee employee, Task task, int progress, boolean completed) {
        this.id = id;
        this.employee = employee;
        this.task = task;
        this.progress = progress;
        this.completed = completed;
    }

    public EmployeeTask () {}
}