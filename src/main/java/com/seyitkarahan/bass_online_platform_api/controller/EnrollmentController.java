package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.EnrollmentCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.EnrollmentResponse;
import com.seyitkarahan.bass_online_platform_api.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/enrollments")
@PreAuthorize("hasRole('STUDENT')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enroll(
            @RequestBody @Valid EnrollmentCreateRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                enrollmentService.enroll(
                        request,
                        authentication.getName()
                )
        );
    }
}
