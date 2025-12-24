package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.response.StudentCourseResponse;
import com.seyitkarahan.bass_online_platform_api.repository.StudentCourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/courses")
@PreAuthorize("hasRole('STUDENT')")
public class StudentCourseController {

    private final StudentCourseRepository repository;
    private final StudentRepository studentRepository;

    public StudentCourseController(
            StudentCourseRepository repository,
            StudentRepository studentRepository
    ) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/my")
    public ResponseEntity<List<StudentCourseResponse>> myCourses(
            Authentication authentication
    ) {

        Long studentId = studentRepository
                .findStudentIdByUserEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return ResponseEntity.ok(
                repository.findMyCourses(studentId)
        );
    }
}
