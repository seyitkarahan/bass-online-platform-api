package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.CourseCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.request.CourseUpdateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CourseResponse;
import com.seyitkarahan.bass_online_platform_api.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor/courses")
@PreAuthorize("hasRole('INSTRUCTOR')")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(
            @RequestBody @Valid CourseCreateRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                courseService.createCourse(request, authentication.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody CourseUpdateRequest request,
            Authentication authentication
    ) {
        courseService.updateCourse(id, request, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        courseService.deleteCourse(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
