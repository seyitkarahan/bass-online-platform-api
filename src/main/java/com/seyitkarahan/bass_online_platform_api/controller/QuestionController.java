package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.request.QuestionCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.QuestionResponse;
import com.seyitkarahan.bass_online_platform_api.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<Void> addQuestion(
            @RequestBody QuestionCreateRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        questionService.addQuestion(
                request,
                user.getUsername()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionResponse>> getQuizQuestions(
            @PathVariable Long quizId
    ) {
        return ResponseEntity.ok(
                questionService.getQuizQuestions(quizId)
        );
    }
}
