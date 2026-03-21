package com.example.sber.service;

import com.example.sber.model.entity.DailyResult;
import com.example.sber.model.entity.Employee;
import com.example.sber.repository.DailyResultRepository;
import com.example.sber.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}