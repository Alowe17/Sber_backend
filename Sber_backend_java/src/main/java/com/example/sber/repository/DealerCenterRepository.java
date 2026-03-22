package com.example.sber.repository;

import com.example.sber.model.entity.DealerCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DealerCenterRepository extends JpaRepository<DealerCenter,Long> {

    List<DealerCenter> findByCode(String code);

    List<DealerCenter> findByNameContainingIgnoreCase(String name);

    List<DealerCenter> findByRegionId(Long regionId);
}