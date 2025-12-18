package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Quiz;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Quiz> findByCourseId(Long courseId) {
        String sql = "SELECT * FROM quizzes WHERE course_id = ?";
        return jdbcTemplate.query(sql, (rs, i) ->
                Quiz.builder()
                        .id(rs.getLong("id"))
                        .courseId(rs.getLong("course_id"))
                        .title(rs.getString("title"))
                        .build(), courseId
        ).stream().findFirst();
    }

    public Long save(Quiz quiz) {
        String sql = """
            INSERT INTO quizzes (course_id, title)
            VALUES (?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                quiz.getCourseId(),
                quiz.getTitle()
        );
    }
}
