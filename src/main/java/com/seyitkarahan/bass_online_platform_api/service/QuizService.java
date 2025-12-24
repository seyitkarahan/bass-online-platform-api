package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.QuizCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.QuizResponse;
import com.seyitkarahan.bass_online_platform_api.repository.CourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;

    public QuizService(
            QuizRepository quizRepository,
            CourseRepository courseRepository
    ) {
        this.quizRepository = quizRepository;
        this.courseRepository = courseRepository;
    }

    public Long createQuiz(QuizCreateRequest request, String instructorEmail) {

        boolean ownsCourse =
                courseRepository.isInstructorOwnerOfCourse(
                        request.getCourseId(),
                        instructorEmail
                );

        if (!ownsCourse) {
            throw new RuntimeException("Unauthorized");
        }

        return quizRepository.save(
                request.getCourseId(),
                request.getTitle()
        );
    }

    public List<QuizResponse> getCourseQuizzes(Long courseId) {
        return quizRepository.findByCourse(courseId);
    }
}
