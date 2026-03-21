package com.example.sber.repository;

import com.example.sber.model.entity.Region;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository {

    Optional<Region> findByCode(String code);

    List<Region> findByNameContainingIgnoreCase(String name);
}