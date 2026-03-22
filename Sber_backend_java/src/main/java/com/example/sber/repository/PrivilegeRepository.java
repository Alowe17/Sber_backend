package com.example.sber.repository;

import com.example.sber.model.entity.Privilege;
import com.example.sber.model.enums.CurrentLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {

    List<Privilege> findAll();

    List<Privilege> findByRequiredLevel(CurrentLevel requiredLevel);

    List<Privilege> findByNameContainingIgnoreCase(String name);
}