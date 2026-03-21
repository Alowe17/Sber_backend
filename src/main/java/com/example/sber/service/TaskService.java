package com.example.sber.service;

import com.example.sber.model.entity.EmployeeTask;
import com.example.sber.model.entity.Task;
import com.example.sber.repository.EmployeeTaskRepository;
import com.example.sber.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeTaskRepository employeeTaskRepository;

    public TaskService(TaskRepository taskRepository, EmployeeTaskRepository employeeTaskRepository) {
        this.taskRepository = taskRepository;
        this.employeeTaskRepository = employeeTaskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<EmployeeTask> getEmployeeTasks(Long employeeId) {
        return employeeTaskRepository.findByEmployeeId(employeeId);
    }

    public List<EmployeeTask> getEmployeeTasks(Long employeeId, Long taskId, Boolean completed) {
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
