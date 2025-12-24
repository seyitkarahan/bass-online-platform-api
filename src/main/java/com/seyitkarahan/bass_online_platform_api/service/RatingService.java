package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.RatingCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.RatingResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Rating;
import com.seyitkarahan.bass_online_platform_api.repository.CourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.NotificationRepository;
import com.seyitkarahan.bass_online_platform_api.repository.RatingRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final NotificationRepository notificationRepository;

    public RatingService(RatingRepository ratingRepository,
                         StudentRepository studentRepository, CourseRepository courseRepository, NotificationRepository notificationRepository) {
        this.ratingRepository = ratingRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.notificationRepository = notificationRepository;
    }

    public void addRating(String userEmail, RatingCreateRequest request) {

        Long studentId = studentRepository
                .findStudentIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ratingRepository.save(studentId, request);

        Long instructorUserId = courseRepository
                .findInstructorUserId(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        notificationRepository.save(
                instructorUserId,
                "Your course received a new rating",
                null
        );
    }

    public List<RatingResponse> getCourseRatings(Long courseId) {
        return ratingRepository.findByCourse(courseId);
    }
}
