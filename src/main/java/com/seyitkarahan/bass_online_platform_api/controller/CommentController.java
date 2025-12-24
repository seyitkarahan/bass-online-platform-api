package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.CommentCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CommentResponse;
import com.seyitkarahan.bass_online_platform_api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public void addComment(
            @AuthenticationPrincipal String email,
            @RequestBody CommentCreateRequest request
    ) {
        commentService.addComment(email, request);
    }

    @GetMapping("/course/{courseId}")
    public List<CommentResponse> getComments(@PathVariable Long courseId) {
        return commentService.getCourseComments(courseId);
    }
}
