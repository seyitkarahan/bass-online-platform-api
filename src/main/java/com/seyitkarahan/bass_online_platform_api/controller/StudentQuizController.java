package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.QuizSubmitRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.QuizResultResponse;
import com.seyitkarahan.bass_online_platform_api.service.QuizSolveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/quizzes")
public class StudentQuizController {

    private final QuizSolveService quizSolveService;

    public StudentQuizController(QuizSolveService quizSolveService) {
        this.quizSolveService = quizSolveService;
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultResponse> submitQuiz(
            @RequestBody QuizSubmitRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(
                quizSolveService.submitQuiz(
                        request,
                        userDetails.getUsername()
                )
        );
    }
}
