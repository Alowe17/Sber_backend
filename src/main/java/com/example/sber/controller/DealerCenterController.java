package com.example.sber.controller;

import com.example.sber.model.entity.DealerCenter;
import com.example.sber.service.EmployeeService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealer-centers")
@RequiredArgsConstructor
@Validated
public class DealerCenterController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<DealerCenter> getDealerCenters(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @Positive Long regionId) {
        return employeeService.getDealerCenters(code, name, regionId);
    }

    @GetMapping("/{id}")
    public DealerCenter getDealerCenterById(@PathVariable @Positive Long id) {
        return employeeService.getDealerCenterById(id);
    }
}
