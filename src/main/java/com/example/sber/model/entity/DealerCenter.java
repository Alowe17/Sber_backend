package com.example.sber.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="dealer_centers")
public class DealerCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String address;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    public DealerCenter (Long id, String code, String name, String address, Region region) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.address = address;
        this.region = region;
    }

    public DealerCenter() {}
}