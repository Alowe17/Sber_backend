package com.example.sber.controller;

import com.example.sber.model.entity.EmployeeTask;
import com.example.sber.service.TaskService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-tasks")
@RequiredArgsConstructor
@Validated
public class EmployeeTaskController {

    private final TaskService taskService;

    @GetMapping
    public List<EmployeeTask> getEmployeeTasks(
            @RequestParam(required = false) @Positive Long employeeId,
            @RequestParam(required = false) @Positive Long taskId,
            @RequestParam(required = false) Boolean completed) {
        return taskService.getEmployeeTasks(employeeId, taskId, completed);
    }
}
