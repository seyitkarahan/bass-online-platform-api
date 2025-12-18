package com.seyitkarahan.bass_online_platform_api.repository;

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

    public List<Lesson> findByCourseId(Long courseId) {
        String sql = "SELECT * FROM lessons WHERE course_id = ?";
        return jdbcTemplate.query(sql, (rs, i) ->
                Lesson.builder()
                        .id(rs.getLong("id"))
                        .courseId(rs.getLong("course_id"))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .build(), courseId
        );
    }

    public Long save(Lesson lesson) {
        String sql = """
            INSERT INTO lessons (course_id, title, content)
            VALUES (?, ?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                lesson.getCourseId(),
                lesson.getTitle(),
                lesson.getContent()
        );
    }
}
