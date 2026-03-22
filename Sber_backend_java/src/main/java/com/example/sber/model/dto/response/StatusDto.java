package com.example.sber.model.dto.response;

import com.example.sber.model.enums.CurrentLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusDto {
    private CurrentLevel currentLevel;
    private int currentPoints;
    private int pointsToNext;
    private int progressPercent;
    private CurrentLevel nextLevel;

    public StatusDto (CurrentLevel currentLevel, int currentPoints, int pointsToNext, int progressPercent, CurrentLevel nextLevel) {
        this.currentLevel = currentLevel;
        this.currentPoints = currentPoints;
        this.pointsToNext = pointsToNext;
        this.progressPercent = progressPercent;
        this.nextLevel = nextLevel;
    }

    public StatusDto () {}
}