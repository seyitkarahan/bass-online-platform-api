package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.LessonCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.LessonResponse;
import com.seyitkarahan.bass_online_platform_api.service.LessonService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public void create(
            @RequestBody LessonCreateRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        lessonService.createLesson(request, user.getUsername());
    }

    @GetMapping("/course/{courseId}")
    public List<LessonResponse> getByCourse(@PathVariable Long courseId) {
        return lessonService.getCourseLessons(courseId);
    }
}
