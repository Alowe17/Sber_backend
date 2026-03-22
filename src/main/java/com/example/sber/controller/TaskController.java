package com.example.sber.controller;

import com.example.sber.model.entity.EmployeeTask;
import com.example.sber.model.entity.Task;
import com.example.sber.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/my")
    public List<EmployeeTask> getMyTasks(@RequestParam Long employeeId) {
        return taskService.getEmployeeTasks(employeeId);
    }
}
