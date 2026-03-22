package com.example.sber.controller;

import com.example.sber.model.entity.Employee;
import com.example.sber.model.enums.CurrentLevel;
import com.example.sber.model.enums.Role;
import com.example.sber.service.EmployeeService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) CurrentLevel currentLevel,
            @RequestParam(required = false) @Positive Long dealerCenterId,
            @RequestParam(required = false) String fullName) {
        return employeeService.getEmployees(role, currentLevel, dealerCenterId, fullName);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable @Positive Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/by-email")
    public Employee getEmployeeByEmail(@RequestParam @Email String email) {
        return employeeService.getEmployeeByEmail(email);
    }
}
