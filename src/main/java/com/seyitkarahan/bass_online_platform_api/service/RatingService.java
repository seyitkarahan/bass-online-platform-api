package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.RatingCreateRequest;
import com.seyitkarahan.bass_online_platform_api.entity.Rating;
import com.seyitkarahan.bass_online_platform_api.repository.RatingRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;

    public RatingService(RatingRepository ratingRepository,
                         StudentRepository studentRepository) {
        this.ratingRepository = ratingRepository;
        this.studentRepository = studentRepository;
    }

    public void rate(
            RatingCreateRequest request,
            String userEmail
    ) {

        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!ratingRepository.hasEnrollment(studentId, request.getCourseId())) {
            throw new RuntimeException("Not enrolled in this course");
        }

        if (ratingRepository.alreadyRated(studentId, request.getCourseId())) {
            throw new RuntimeException("Already rated");
        }

        ratingRepository.save(
                Rating.builder()
                        .studentId(studentId)
                        .courseId(request.getCourseId())
                        .rating(request.getRating())
                        .comment(request.getComment())
                        .build()
        );
    }
}
