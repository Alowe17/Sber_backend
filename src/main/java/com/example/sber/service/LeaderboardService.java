package com.example.sber.service;

import com.example.sber.model.entity.Employee;
import com.example.sber.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {
    private final EmployeeRepository employeeRepository;

    public LeaderboardService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getTopEmployees() {
        return employeeRepository.findTop10ByOrderByRatingPointsDesc();
    }
}