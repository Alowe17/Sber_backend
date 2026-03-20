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

    public Employee (Long id, String fullName, String email, String password, String phone, CurrentLevel currentLevel, Role role, DealerCenter dealerCenter, int ratingPoints, int sberId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.currentLevel = currentLevel;
        this.role = role;
        this.dealerCenter = dealerCenter;
        this.ratingPoints = ratingPoints;
        this.sberId = sberId;
    }

    public Employee() {}
}