package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.response.MyCourseResponse;
import com.seyitkarahan.bass_online_platform_api.service.MyCourseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my-courses")
public class MyCourseController {

    private final MyCourseService myCourseService;

    public MyCourseController(MyCourseService myCourseService) {
        this.myCourseService = myCourseService;
    }

    @GetMapping
    public List<MyCourseResponse> getMyCourses(
            @AuthenticationPrincipal String userEmail
    ) {
        return myCourseService.getMyCourses(userEmail);
    }
}

