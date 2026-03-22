package com.example.sber.controller;

import com.example.sber.model.entity.Employee;
import com.example.sber.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final EmployeeService employeeService;

    @GetMapping
    public Employee getProfile(@RequestHeader("X-User-Id") Long employeeId) {
        return employeeService.getProfile(employeeId);
    }
}
