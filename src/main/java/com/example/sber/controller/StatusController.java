package com.example.sber.controller;

import com.example.sber.model.dto.response.StatusDto;
import com.example.sber.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/current")
    public StatusDto getCurrentStatus(@RequestHeader("X-User-Id") Long employeeId) {
        return statusService.getStatus(employeeId);
    }
}