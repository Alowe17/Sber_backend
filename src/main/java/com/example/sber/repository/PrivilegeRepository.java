package com.example.sber.repository;

import com.example.sber.model.entity.Privilege;
import com.example.sber.model.enums.CurrentLevel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository {

    List<Privilege> findByRequiredLevel(CurrentLevel requiredLevel);

    List<Privilege> findByNameContainingIgnoreCase(String name);
}