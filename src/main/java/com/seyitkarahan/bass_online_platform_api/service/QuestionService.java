package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.QuestionCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.QuestionResponse;
import com.seyitkarahan.bass_online_platform_api.repository.CourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.QuestionRepository;
import com.seyitkarahan.bass_online_platform_api.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            QuizRepository quizRepository,
            CourseRepository courseRepository
    ) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.courseRepository = courseRepository;
    }

    public void addQuestion(
            QuestionCreateRequest request,
            String instructorEmail
    ) {
        boolean ownsQuiz =
                courseRepository.isInstructorOwnerOfQuiz(
                        request.getQuizId(),
                        instructorEmail
                );

        if (!ownsQuiz) {
            throw new RuntimeException("Unauthorized");
        }

        questionRepository.save(
                request.getQuizId(),
                request.getQuestionText(),
                request.getAnswer()
        );
    }

    public List<QuestionResponse> getQuizQuestions(Long quizId) {
        return questionRepository.findByQuiz(quizId);
    }
}
