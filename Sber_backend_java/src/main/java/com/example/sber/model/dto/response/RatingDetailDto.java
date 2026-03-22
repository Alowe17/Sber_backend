package com.example.sber.model.dto.response;

import com.example.sber.model.enums.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDetailDto {
    private EventType type;
    private long points;

    public RatingDetailDto (EventType type, long points) {
        this.type = type;
        this.points = points;
    }

    public RatingDetailDto () {}
}