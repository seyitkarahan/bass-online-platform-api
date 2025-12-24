package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.QuizCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.QuizResponse;
import com.seyitkarahan.bass_online_platform_api.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<Long> createQuiz(
            @RequestBody QuizCreateRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long quizId = quizService.createQuiz(
                request,
                user.getUsername()
        );
        return ResponseEntity.ok(quizId);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<QuizResponse>> getCourseQuizzes(
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(
                quizService.getCourseQuizzes(courseId)
        );
    }
}
