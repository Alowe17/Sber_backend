package com.example.sber.controller;

import com.example.sber.model.dto.response.StatusDto;
import com.example.sber.service.StatusService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/{id}")
    public StatusDto getStatus(@PathVariable Long id) {
        return statusService.getStatus(id);
    }
}