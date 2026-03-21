package com.example.sber.service;

import com.example.sber.model.entity.Employee;
import com.example.sber.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getProfile(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow();
    }
}