package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.CommentCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CommentResponse;
import com.seyitkarahan.bass_online_platform_api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Void> comment(
            @RequestBody @Valid CommentCreateRequest request,
            Authentication authentication
    ) {
        commentService.addComment(
                request,
                authentication.getName()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CommentResponse>> comments(
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(
                commentService.getCourseComments(courseId)
        );
    }
}
