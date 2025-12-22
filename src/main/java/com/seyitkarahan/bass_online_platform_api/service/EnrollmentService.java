package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.EnrollmentCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.EnrollmentResponse;
import com.seyitkarahan.bass_online_platform_api.repository.EnrollmentRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }

    public EnrollmentResponse enroll(
            EnrollmentCreateRequest request,
            String userEmail
    ) {

        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (enrollmentRepository.exists(studentId, request.getCourseId())) {
            throw new RuntimeException("Already enrolled in this course");
        }

        Long enrollmentId = enrollmentRepository
                .save(studentId, request.getCourseId());

        return new EnrollmentResponse(
                enrollmentId,
                request.getCourseId(),
                true
        );
    }
}

