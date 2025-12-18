package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Rating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public RatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Rating> findByStudentAndCourse(Long studentId, Long courseId) {
        String sql = """
            SELECT * FROM ratings
            WHERE student_id = ? AND course_id = ?
        """;

        return jdbcTemplate.query(sql, (rs, i) ->
                Rating.builder()
                        .id(rs.getLong("id"))
                        .studentId(rs.getLong("student_id"))
                        .courseId(rs.getLong("course_id"))
                        .rating(rs.getInt("rating"))
                        .comment(rs.getString("comment"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build(), studentId, courseId
        ).stream().findFirst();
    }

    public Long save(Rating rating) {
        String sql = """
            INSERT INTO ratings (student_id, course_id, rating, comment)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                rating.getStudentId(),
                rating.getCourseId(),
                rating.getRating(),
                rating.getComment()
        );
    }
}
