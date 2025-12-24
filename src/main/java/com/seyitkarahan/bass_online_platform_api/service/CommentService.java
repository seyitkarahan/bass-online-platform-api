package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.CommentCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CommentResponse;
import com.seyitkarahan.bass_online_platform_api.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final NotificationRepository notificationRepository;


    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository, StudentRepository studentRepository, CourseRepository courseRepository, NotificationRepository notificationRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.notificationRepository = notificationRepository;
    }

    public void addComment(String email, CommentCreateRequest request) {

        Long userId = userRepository.findIdByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long studentId = studentRepository.findStudentIdByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        commentRepository.save(userId, studentId, request);

        Long instructorUserId = courseRepository
                .findInstructorUserId(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        notificationRepository.save(
                instructorUserId,
                "New comment added to your course",
                null
        );
    }

    public List<CommentResponse> getCourseComments(Long courseId) {
        return commentRepository.findByCourse(courseId);
    }
}
