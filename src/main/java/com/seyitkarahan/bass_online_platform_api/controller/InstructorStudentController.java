package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.response.InstructorStudentResponse;
import com.seyitkarahan.bass_online_platform_api.service.InstructorStudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor/students")
public class InstructorStudentController {

    private final InstructorStudentService studentService;

    public InstructorStudentController(InstructorStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{courseId}")
    public List<InstructorStudentResponse> getStudents(
            @AuthenticationPrincipal String email,
            @PathVariable Long courseId
    ) {
        return studentService.getStudents(email, courseId);
    }
}

