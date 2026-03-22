package com.example.sber.service;

import com.example.sber.model.entity.DealerCenter;
import com.example.sber.model.entity.Employee;
import com.example.sber.model.entity.LevelConfig;
import com.example.sber.model.entity.LevelThresholds;
import com.example.sber.model.entity.Region;
import com.example.sber.model.enums.CurrentLevel;
import com.example.sber.model.enums.EventType;
import com.example.sber.model.enums.Role;
import com.example.sber.repository.DealerCenterRepository;
import com.example.sber.repository.EmployeeRepository;
import com.example.sber.repository.LevelConfigRepository;
import com.example.sber.repository.LevelThresholdsRepository;
import com.example.sber.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DealerCenterRepository dealerCenterRepository;
    private final RegionRepository regionRepository;
    private final LevelConfigRepository levelConfigRepository;
    private final LevelThresholdsRepository levelThresholdsRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DealerCenterRepository dealerCenterRepository,
            RegionRepository regionRepository,
            LevelConfigRepository levelConfigRepository,
            LevelThresholdsRepository levelThresholdsRepository) {
        this.employeeRepository = employeeRepository;
        this.dealerCenterRepository = dealerCenterRepository;
        this.regionRepository = regionRepository;
        this.levelConfigRepository = levelConfigRepository;
        this.levelThresholdsRepository = levelThresholdsRepository;
    }

    public Employee getProfile(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow();
    }

    public List<Employee> getEmployees(Role role, CurrentLevel currentLevel, Long dealerCenterId, String fullName) {
        if (role != null) {
            return employeeRepository.findByRole(role);
        }
        if (currentLevel != null) {
            return employeeRepository.findByCurrentLevel(currentLevel);
        }
        if (dealerCenterId != null) {
            return employeeRepository.findByDealerCenterId(dealerCenterId);
        }
        if (fullName != null && !fullName.isBlank()) {
            return employeeRepository.findByFullNameContainingIgnoreCase(fullName);
        }
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElseThrow();
    }

    public List<DealerCenter> getDealerCenters(String code, String name, Long regionId) {
        if (code != null && !code.isBlank()) {
            return dealerCenterRepository.findByCode(code);
        }
        if (name != null && !name.isBlank()) {
            return dealerCenterRepository.findByNameContainingIgnoreCase(name);
        }
        if (regionId != null) {
            return dealerCenterRepository.findByRegionId(regionId);
        }
        return dealerCenterRepository.findAll();
    }

    public DealerCenter getDealerCenterById(Long id) {
        return dealerCenterRepository.findById(id).orElseThrow();
    }

    public List<Region> getRegions(String name) {
        if (name != null && !name.isBlank()) {
            return regionRepository.findByNameContainingIgnoreCase(name);
        }
        return regionRepository.findAll();
    }

    public Region getRegionById(Long id) {
        return regionRepository.findById(id).orElseThrow();
    }

    public Region getRegionByCode(String code) {
        return regionRepository.findByCode(code).orElseThrow();
    }

    public List<LevelConfig> getLevelConfig() {
        return levelConfigRepository.findAll();
    }

    public LevelConfig getLevelConfigByLevel(CurrentLevel currentLevel) {
        return levelConfigRepository.findByCurrentLevel(currentLevel).orElseThrow();
    }

    public List<LevelThresholds> getThresholds(Long employeeId, EventType eventType) {
        if (employeeId != null && eventType != null) {
            return levelThresholdsRepository.findByEmployeeIdAndEventType(employeeId, eventType);
        }
        if (employeeId != null) {
            return levelThresholdsRepository.findByEmployeeId(employeeId);
        }
        if (eventType != null) {
            return levelThresholdsRepository.findByEventType(eventType);
        }
        return levelThresholdsRepository.findAll();
    }
}