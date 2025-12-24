package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.response.InstructorStudentResponse;
import com.seyitkarahan.bass_online_platform_api.repository.EnrollmentRepository;
import com.seyitkarahan.bass_online_platform_api.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorStudentService {

    private final EnrollmentRepository enrollmentRepository;
    private final InstructorRepository instructorRepository;

    public InstructorStudentService(
            EnrollmentRepository enrollmentRepository,
            InstructorRepository instructorRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<InstructorStudentResponse> getStudents(
            String instructorEmail,
            Long courseId
    ) {
        Long instructorId = instructorRepository
                .findInstructorIdByUserEmail(instructorEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        return enrollmentRepository.findStudentsByCourse(
                instructorId,
                courseId
        );
    }
}
