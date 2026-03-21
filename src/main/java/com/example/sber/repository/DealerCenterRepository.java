package com.example.sber.repository;

import com.example.sber.model.entity.DealerCenter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerCenterRepository {

    List<DealerCenter> findByCode(String code);

    List<DealerCenter> findByNameContainingIgnoreCase(String name);

    List<DealerCenter> findByRegionId(Long regionId);
}