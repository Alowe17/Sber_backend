package com.example.sber.controller;

import com.example.sber.model.entity.Region;
import com.example.sber.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionRepository regionRepository;

    @GetMapping
    public List<Region> getRegions(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return regionRepository.findByNameContainingIgnoreCase(name);
        }
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Region getRegionById(@PathVariable Long id) {
        return regionRepository.findById(id).orElseThrow();
    }

    @GetMapping("/by-code")
    public Region getRegionByCode(@RequestParam String code) {
        return regionRepository.findByCode(code).orElseThrow();
    }
}
