package com.example.sber.controller;

import com.example.sber.model.entity.Employee;
import com.example.sber.model.enums.CurrentLevel;
import com.example.sber.model.enums.Role;
import com.example.sber.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) CurrentLevel currentLevel,
            @RequestParam(required = false) Long dealerCenterId,
            @RequestParam(required = false) String fullName) {

        if (role != null) {
            return employeeRepository.findByRole(role);
        }
        if (currentLevel != null) {
            return employeeRepository.findByCurrentLevel(currentLevel);
        }
        if (dealerCenterId != null) {
            return employeeRepository.findByDealerCenterId(dealerCenterId);
        }
        if (fullName != null && !fullName.isBlank()) {
            return employeeRepository.findByFullNameContainingIgnoreCase(fullName);
        }
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @GetMapping("/by-email")
    public Employee getEmployeeByEmail(@RequestParam String email) {
        return employeeRepository.findByEmail(email).orElseThrow();
    }
}
