package com.example.sber.repository;

import com.example.sber.model.entity.Employee;
import com.example.sber.model.enums.CurrentLevel;
import com.example.sber.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByRole(Role role);

    List<Employee> findByDealerCenterId(Long dealerCenterId);

    List<Employee> findByCurrentLevel(CurrentLevel currentLevel);

    List<Employee> findByFullNameContainingIgnoreCase(String fullName);

    List<Employee> findTop10ByOrderByRatingPointsDesc();
}