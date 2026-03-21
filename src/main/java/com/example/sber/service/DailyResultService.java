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

    public DailyResultService (DailyResultRepository dailyResultRepository, EmployeeRepository employeeRepository) {
        this.dailyResultRepository = dailyResultRepository;
        this.employeeRepository = employeeRepository;
    }

    public void createDailyResult (int deals, double volume, int products, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        DailyResult dailyResult = new DailyResult();
        dailyResult.setEmployee(employee);
        dailyResult.setCreditVolume(volume);
        dailyResult.setDealsCount(deals);
        dailyResult.setProductCount(products);
        dailyResult.setDate(LocalDate.now());

        dailyResultRepository.save(dailyResult);
    }
}