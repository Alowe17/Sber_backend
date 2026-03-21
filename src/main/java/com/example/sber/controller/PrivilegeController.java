package com.example.sber.controller;

import com.example.sber.model.dto.response.PrivilegeDto;
import com.example.sber.service.PrivilegeServer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privileges")
@RequiredArgsConstructor
public class PrivilegeController {

    private final PrivilegeServer privilegeServer;

    @GetMapping
    public List<PrivilegeDto> getPrivileges(@RequestHeader("X-User-Id") Long employeeId) {
        return privilegeServer.getPrivileges(employeeId);
    }
}