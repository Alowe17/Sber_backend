package com.example.sber.controller;

import com.example.sber.model.entity.DailyResult;
import com.example.sber.service.DailyResultService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/daily-results")
@RequiredArgsConstructor
@Validated
public class DailyResultController {

    private final DailyResultService dailyResultService;

    @PostMapping
    public Map<String, Object> saveDailyResults(
            @RequestParam @Positive Long employeeId,
            @Valid @RequestBody DailyResultRequest request) {
        dailyResultService.createDailyResult(
                employeeId,
                request.dealsCount(),
                request.volumeAmount(),
                request.productsCount()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Результаты сохранены");
        return response;
    }

    @GetMapping("/today")
    public Map<String, Object> getTodayResults(@RequestParam @Positive Long employeeId) {
        return dailyResultService.getTodayResults(employeeId);
    }

    @GetMapping("/month")
    public List<DailyResult> getMonthResults(
            @RequestParam @Positive Long employeeId,
            @RequestParam @Min(2000) int year,
            @RequestParam @Min(1) @Max(12) int month) {
        return dailyResultService.getMonthResults(employeeId, year, month);
    }

    public record DailyResultRequest(
            @NotNull @PositiveOrZero Integer dealsCount,
            @NotNull @PositiveOrZero Double volumeAmount,
            @NotNull @PositiveOrZero Integer productsCount
    ) {
    }
}