package com.example.sber.repository;

import com.example.sber.model.entity.EmployeeTask;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeTaskRepository {

    List<EmployeeTask> findByEmployeeId(Long employeeId);

    List<EmployeeTask> findByTaskId(Long taskId);

    List<EmployeeTask> findByEmployeeIdAndCompleted(Long employeeId, boolean completed);

    List<EmployeeTask> findByEmployeeIdAndTaskId(Long employeeId, Long taskId);
}