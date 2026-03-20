package com.example.sber.model.entity;


import com.example.sber.model.enums.CorrentLevel;
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
    private CorrentLevel correntLevel;
    private Role role;
    @ManyToOne
    private DealerCenter dealerCenter;
    private LocalDate registrationDate;
    private int ratingPoints;
    private int sberId;

}
