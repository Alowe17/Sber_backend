package com.example.sber.controller;

import com.example.sber.model.entity.EmployeeTask;
import com.example.sber.repository.EmployeeTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-tasks")
@RequiredArgsConstructor
public class EmployeeTaskController {

    private final EmployeeTaskRepository employeeTaskRepository;

    @GetMapping
    public List<EmployeeTask> getEmployeeTasks(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) Boolean completed) {

        if (employeeId != null && taskId != null) {
            return employeeTaskRepository.findByEmployeeIdAndTaskId(employeeId, taskId);
        }
        if (employeeId != null && completed != null) {
            return employeeTaskRepository.findByEmployeeIdAndCompleted(employeeId, completed);
        }
        if (employeeId != null) {
            return employeeTaskRepository.findByEmployeeId(employeeId);
        }
        if (taskId != null) {
            return employeeTaskRepository.findByTaskId(taskId);
        }
        return employeeTaskRepository.findAll();
    }
}
