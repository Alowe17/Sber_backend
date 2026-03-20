package com.example.sber.model.entity;


import com.example.sber.model.enums.CurrentLevel;
import com.example.sber.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private CurrentLevel currentLevel;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "dealer_center_id")
    private DealerCenter dealerCenter;
    private LocalDate registrationDate;
    private int ratingPoints;
    private int sberId;
}