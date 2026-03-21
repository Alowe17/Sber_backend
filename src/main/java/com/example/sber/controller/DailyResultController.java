package com.example.sber.controller;

import com.example.sber.model.entity.DailyResult;
import com.example.sber.repository.DailyResultRepository;
import com.example.sber.service.DailyResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/daily-results")
@RequiredArgsConstructor
public class DailyResultController {

    private final DailyResultService dailyResultService;
    private final DailyResultRepository dailyResultRepository;

    @PostMapping
    public Map<String, Object> saveDailyResults(
            @RequestHeader("X-User-Id") Long employeeId,
            @RequestBody Map<String, Object> request) {

        int deals = ((Number) request.getOrDefault("dealsCount", 0)).intValue();
        double volume = ((Number) request.getOrDefault("volumeAmount", 0)).doubleValue();
        int products = ((Number) request.getOrDefault("productsCount", 0)).intValue();

        dailyResultService.createDailyResult(employeeId, deals, volume, products);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Результаты сохранены");
        return response;
    }

    @GetMapping("/today")
    public Map<String, Object> getTodayResults(@RequestHeader("X-User-Id") Long employeeId) {
        List<DailyResult> results = dailyResultRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());

        if (results.isEmpty()) {
            response.put("dealsCount", 0);
            response.put("volumeAmount", 0);
            response.put("productsCount", 0);
            response.put("isSubmitted", false);
        } else {
            DailyResult result = results.get(0);
            response.put("dealsCount", result.getDealsCount());
            response.put("volumeAmount", result.getCreditVolume());
            response.put("productsCount", result.getProductCount());
            response.put("isSubmitted", true);
        }

        return response;
    }

    @GetMapping("/month")
    public List<DailyResult> getMonthResults(
            @RequestHeader("X-User-Id") Long employeeId,
            @RequestParam int year,
            @RequestParam int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<DailyResult> allResults = dailyResultRepository.findByEmployeeId(employeeId);

        return allResults.stream()
                .filter(r -> !r.getDate().isBefore(startDate) && !r.getDate().isAfter(endDate))
                .toList();
    }
}