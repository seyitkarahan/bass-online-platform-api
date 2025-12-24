package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.response.CommentResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.CourseResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.UserResponse;
import com.seyitkarahan.bass_online_platform_api.repository.CommentRepository;
import com.seyitkarahan.bass_online_platform_api.repository.CourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CommentRepository commentRepository;

    public AdminService(UserRepository userRepository,
                        CourseRepository courseRepository,
                        CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.commentRepository = commentRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserRole(Long userId, String role) {
        userRepository.updateRole(userId, role);
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll();
    }

    public void deleteCourse(Long courseId) {
        courseRepository.delete(courseId);
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll();
    }

    public void deleteComment(Long commentId) {
        commentRepository.delete(commentId);
    }
}

