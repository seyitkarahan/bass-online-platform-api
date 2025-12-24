package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.CommentCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CommentResponse;
import com.seyitkarahan.bass_online_platform_api.repository.CommentRepository;
import com.seyitkarahan.bass_online_platform_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public void addComment(CommentCreateRequest request, String userEmail) {

        Long userId = userRepository
                .findIdByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        commentRepository.save(
                userId,
                request.getCourseId(),
                request.getContent()
        );
    }


    public List<CommentResponse> getCourseComments(Long courseId) {
        return commentRepository.findByCourse(courseId);
    }
}
