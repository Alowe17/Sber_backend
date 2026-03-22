package com.example.sber.controller;

import com.example.sber.model.dto.response.RatingDetailDto;
import com.example.sber.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping("/details")
    public List<RatingDetailDto> getRatingDetails(@RequestParam Long employeeId) {
        return ratingService.getDetails(employeeId);
    }
}
