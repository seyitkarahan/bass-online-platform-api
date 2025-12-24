package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.LessonCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.LessonResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Lesson;
import com.seyitkarahan.bass_online_platform_api.repository.CourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository,
                         CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public void createLesson(LessonCreateRequest request, String instructorEmail) {

        boolean ownsCourse =
                courseRepository.isInstructorOwnerOfCourse(
                        request.getCourseId(),
                        instructorEmail
                );

        if (!ownsCourse) {
            throw new RuntimeException("Unauthorized");
        }

        lessonRepository.save(
                Lesson.builder()
                        .courseId(request.getCourseId())
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build()
        );
    }

    public List<LessonResponse> getCourseLessons(Long courseId) {
        return lessonRepository.findByCourse(courseId);
    }
}
