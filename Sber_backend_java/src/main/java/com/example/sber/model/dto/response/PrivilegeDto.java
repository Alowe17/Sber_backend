package com.example.sber.model.dto.response;

import com.example.sber.model.enums.CurrentLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivilegeDto {
    private String name;
    private String description;
    private int effectMoney;
    private boolean isActive;
    private CurrentLevel unlockedLevel;

    public PrivilegeDto (String name, String description, int effectMoney, boolean isActive, CurrentLevel unlockedLevel) {
        this.name = name;
        this.description = description;
        this.effectMoney = effectMoney;
        this.isActive = isActive;
        this.unlockedLevel = unlockedLevel;
    }

    public PrivilegeDto () {}
}