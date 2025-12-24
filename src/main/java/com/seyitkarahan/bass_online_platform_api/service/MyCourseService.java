package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.response.MyCourseResponse;
import com.seyitkarahan.bass_online_platform_api.repository.EnrollmentRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyCourseService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public MyCourseService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }

    public List<MyCourseResponse> getMyCourses(String userEmail) {

        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return enrollmentRepository.findMyCourses(studentId);
    }
}

