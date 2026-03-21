package com.example.sber.repository;

import com.example.sber.model.entity.DailyResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyResultRepository extends JpaRepository<DailyResult,Long> {

    List<DailyResult> findByEmployeeId(Long employeeId);

    List<DailyResult> findByDate(LocalDate date);

    List<DailyResult> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}