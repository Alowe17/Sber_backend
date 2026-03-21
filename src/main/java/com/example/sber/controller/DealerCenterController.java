package com.example.sber.controller;

import com.example.sber.model.entity.DealerCenter;
import com.example.sber.repository.DealerCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealer-centers")
@RequiredArgsConstructor
public class DealerCenterController {

    private final DealerCenterRepository dealerCenterRepository;

    @GetMapping
    public List<DealerCenter> getDealerCenters(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long regionId) {

        if (code != null && !code.isBlank()) {
            return dealerCenterRepository.findByCode(code);
        }
        if (name != null && !name.isBlank()) {
            return dealerCenterRepository.findByNameContainingIgnoreCase(name);
        }
        if (regionId != null) {
            return dealerCenterRepository.findByRegionId(regionId);
        }
        return dealerCenterRepository.findAll();
    }

    @GetMapping("/{id}")
    public DealerCenter getDealerCenterById(@PathVariable Long id) {
        return dealerCenterRepository.findById(id).orElseThrow();
    }
}
