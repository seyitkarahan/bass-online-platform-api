package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.QuizSubmitRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.QuizResultResponse;
import com.seyitkarahan.bass_online_platform_api.repository.QuestionRepository;
import com.seyitkarahan.bass_online_platform_api.repository.QuizAttemptRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentAnswerRepository;
import com.seyitkarahan.bass_online_platform_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizSolveService {

    private final StudentRepository studentRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final StudentAnswerRepository answerRepository;

    public QuizSolveService(
            StudentRepository studentRepository,
            QuestionRepository questionRepository,
            QuizAttemptRepository attemptRepository,
            StudentAnswerRepository answerRepository
    ) {
        this.studentRepository = studentRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.answerRepository = answerRepository;
    }

    public QuizResultResponse submitQuiz(
            QuizSubmitRequest request,
            String studentEmail
    ) {
        Long studentId = studentRepository
                .findStudentIdByUserEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        int totalQuestions =
                questionRepository.countByQuiz(request.getQuizId());

        Long attemptId =
                attemptRepository.createAttempt(
                        studentId,
                        request.getQuizId(),
                        totalQuestions
                );

        int correctCount = 0;

        for (var answer : request.getAnswers()) {

            String correctAnswer =
                    questionRepository.findCorrectAnswer(
                            answer.getQuestionId()
                    );

            boolean isCorrect =
                    correctAnswer.equalsIgnoreCase(
                            answer.getAnswer()
                    );

            if (isCorrect) {
                correctCount++;
            }

            answerRepository.save(
                    attemptId,
                    answer.getQuestionId(),
                    answer.getAnswer(),
                    isCorrect
            );
        }

        int score =
                (int) ((double) correctCount / totalQuestions * 100);

        attemptRepository.completeAttempt(
                attemptId,
                correctCount,
                score
        );

        return QuizResultResponse.builder()
                .totalQuestions(totalQuestions)
                .correctAnswers(correctCount)
                .score(score)
                .build();
    }
}
