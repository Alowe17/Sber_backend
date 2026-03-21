package com.example.sber.service;

import com.example.sber.model.entity.EmployeeTask;
import com.example.sber.model.entity.Task;
import com.example.sber.repository.EmployeeRepository;
import com.example.sber.repository.EmployeeTaskRepository;
import com.example.sber.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeTaskRepository employeeTaskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository, EmployeeTaskRepository employeeTaskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeTaskRepository = employeeTaskRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<EmployeeTask> getEmployeeTasks(Long employeeId) {
        return employeeTaskRepository.findByEmployeeId(employeeId);
    }
}
