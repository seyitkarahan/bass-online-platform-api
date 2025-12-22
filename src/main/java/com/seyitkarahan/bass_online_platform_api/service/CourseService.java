package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.request.CourseCreateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.request.CourseUpdateRequest;
import com.seyitkarahan.bass_online_platform_api.dto.response.CourseResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Course;
import com.seyitkarahan.bass_online_platform_api.repository.CourseRepository;
import com.seyitkarahan.bass_online_platform_api.repository.InstructorRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository,
                         InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    public CourseResponse createCourse(CourseCreateRequest request, String userEmail) {

        Long instructorId = instructorRepository
                .findInstructorIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = Course.builder()
                .instructorId(instructorId)
                .categoryId(request.getCategoryId())
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        Long courseId = courseRepository.save(course);

        return new CourseResponse(
                courseId,
                course.getTitle(),
                course.getDescription(),
                course.getPrice()
        );
    }

    public void updateCourse(Long courseId, CourseUpdateRequest request, String userEmail) {

        Long instructorId = instructorRepository
                .findInstructorIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (!courseRepository.isOwner(courseId, instructorId)) {
            throw new RuntimeException("You are not the owner of this course");
        }

        Course course = courseRepository.findById(courseId);
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        courseRepository.update(course);
    }

    public void deleteCourse(Long courseId, String userEmail) {

        Long instructorId = instructorRepository
                .findInstructorIdByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        if (!courseRepository.isOwner(courseId, instructorId)) {
            throw new RuntimeException("You are not the owner of this course");
        }

        courseRepository.delete(courseId);
    }
}
