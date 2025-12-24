package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.LessonResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Lesson;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LessonRepository {

    private final JdbcTemplate jdbcTemplate;

    public LessonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Lesson lesson) {
        String sql = """
            INSERT INTO lessons (course_id, title, content)
            VALUES (?, ?, ?)
        """;
        jdbcTemplate.update(sql,
                lesson.getCourseId(),
                lesson.getTitle(),
                lesson.getContent());
    }

    public List<LessonResponse> findByCourse(Long courseId) {
        String sql = """
            SELECT id, title, content
            FROM lessons
            WHERE course_id = ?
            ORDER BY id
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new LessonResponse(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content")
                ),
                courseId);
    }
}
