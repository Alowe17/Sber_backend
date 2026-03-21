package com.example.sber.repository;

import com.example.sber.model.entity.DailyResult;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyResultRepository {

    List<DailyResult> findByEmployeeId(Long employeeId);

    List<DailyResult> findByDate(LocalDate date);

    List<DailyResult> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}