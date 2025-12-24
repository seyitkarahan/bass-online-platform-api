package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.RatingCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.RatingResponse;
import com.seyitkarahan.bass_online_platform_api.service.RatingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public void addRating(
            @AuthenticationPrincipal String email,
            @RequestBody RatingCreateRequest request
    ) {
        ratingService.addRating(email, request);
    }

    @GetMapping("/course/{courseId}")
    public List<RatingResponse> getRatings(@PathVariable Long courseId) {
        return ratingService.getCourseRatings(courseId);
    }
}
