package com.example.sber.controller;

import com.example.sber.model.entity.Region;
import com.example.sber.service.EmployeeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
@Validated
public class RegionController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Region> getRegions(@RequestParam(required = false) String name) {
        return employeeService.getRegions(name);
    }

    @GetMapping("/{id}")
    public Region getRegionById(@PathVariable @Positive Long id) {
        return employeeService.getRegionById(id);
    }

    @GetMapping("/by-code")
    public Region getRegionByCode(@RequestParam @NotBlank String code) {
        return employeeService.getRegionByCode(code);
    }
}
