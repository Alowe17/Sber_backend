package com.example.sber.service;

import com.example.sber.model.entity.DailyResult;
import com.example.sber.model.entity.Employee;
import com.example.sber.repository.DailyResultRepository;
import com.example.sber.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class DailyResultService {

    private final DailyResultRepository dailyResultRepository;
    private final EmployeeRepository employeeRepository;
    private final RatingService ratingService;

    public DailyResultService(DailyResultRepository dailyResultRepository, EmployeeRepository employeeRepository, RatingService ratingService) {
        this.dailyResultRepository = dailyResultRepository;
        this.employeeRepository = employeeRepository;
        this.ratingService = ratingService;
    }

    public void createDailyResult(Long employeeId, int deals, double volume, int products) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        DailyResult dailyResult = new DailyResult();
        dailyResult.setEmployee(employee);
        dailyResult.setDealsCount(deals);
        dailyResult.setCreditVolume(volume);
        dailyResult.setProductCount(products);
        dailyResult.setDate(LocalDate.now());

        dailyResultRepository.save(dailyResult);

        ratingService.updateRating(employee, deals, volume, products);
    }

    public Map<String, Object> getTodayResults(Long employeeId) {
        List<DailyResult> results = dailyResultRepository.findByEmployeeIdAndDate(employeeId, LocalDate.now());
        DailyResult result = results.stream().findFirst().orElse(null);
        return Map.of(
                "date", LocalDate.now().toString(),
                "dealsCount", result != null ? result.getDealsCount() : 0,
                "volumeAmount", result != null ? result.getCreditVolume() : 0,
                "productsCount", result != null ? result.getProductCount() : 0,
                "isSubmitted", result != null
        );
    }

    public List<DailyResult> getMonthResults(Long employeeId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return dailyResultRepository.findByEmployeeId(employeeId).stream()
                .filter(r -> !r.getDate().isBefore(startDate) && !r.getDate().isAfter(endDate))
                .toList();
    }
}