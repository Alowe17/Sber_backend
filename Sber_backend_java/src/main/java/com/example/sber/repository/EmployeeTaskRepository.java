package com.example.sber.repository;

import com.example.sber.model.entity.EmployeeTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeTaskRepository extends JpaRepository<EmployeeTask,Long> {

    List<EmployeeTask> findByEmployeeId(Long employeeId);

    List<EmployeeTask> findByTaskId(Long taskId);

    List<EmployeeTask> findByEmployeeIdAndCompleted(Long employeeId, boolean completed);

    List<EmployeeTask> findByEmployeeIdAndTaskId(Long employeeId, Long taskId);
}