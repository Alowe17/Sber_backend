package com.example.sber.service;

import com.example.sber.model.dto.response.PrivilegeDto;
import com.example.sber.model.entity.Employee;
import com.example.sber.model.entity.Privilege;
import com.example.sber.repository.EmployeeRepository;
import com.example.sber.repository.PrivilegeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivilegeServer {
    private final EmployeeRepository employeeRepository;
    private final PrivilegeRepository privilegeRepository;
    
    public PrivilegeServer (EmployeeRepository employeeRepository, PrivilegeRepository privilegeRepository) {
        this.employeeRepository = employeeRepository;
        this.privilegeRepository = privilegeRepository;
    }

    public List<PrivilegeDto> getPrivileges (Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        List<Privilege> privilegeList = privilegeRepository.findAll();
        List<PrivilegeDto> privilegeDtoList = new ArrayList<>();

        for (Privilege privilege : privilegeList) {
            PrivilegeDto privilegeDto = new PrivilegeDto();
            privilegeDto.setName(privilege.getName());
            privilegeDto.setDescription(privilege.getDescription());
            privilegeDto.setEffectMoney(privilege.getEffectMoney());
            privilegeDto.setUnlockedLevel(privilege.getRequiredLevel());

            boolean active = employee.getCurrentLevel().ordinal() >= privilege.getRequiredLevel().ordinal();
            privilegeDto.setActive(active);

            privilegeDtoList.add(privilegeDto);
        }
        return privilegeDtoList;
    }
}