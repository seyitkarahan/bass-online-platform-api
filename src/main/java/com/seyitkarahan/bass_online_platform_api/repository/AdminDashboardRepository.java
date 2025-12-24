package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.RecentCommentResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.RecentCourseResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class AdminDashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminDashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long countUsers() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users",
                Long.class
        );
    }

    public long countUsersByRole(String role) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE role = ?",
                Long.class,
                role
        );
    }

    public long countCourses() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM courses",
                Long.class
        );
    }

    public long countActiveEnrollments() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM enrollments WHERE active = true",
                Long.class
        );
    }

    public BigDecimal totalRevenue() {
        return jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(amount), 0) FROM payments WHERE payment_status = 'SUCCESS'",
                BigDecimal.class
        );
    }

    public double averageRating() {
        return jdbcTemplate.queryForObject(
                "SELECT COALESCE(AVG(rating), 0) FROM ratings",
                Double.class
        );
    }

    public List<RecentCourseResponse> recentCourses() {
        String sql = """
            SELECT id, title
            FROM courses
            ORDER BY created_at DESC
            LIMIT 5
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new RecentCourseResponse(
                        rs.getLong("id"),
                        rs.getString("title")
                ));
    }

    public List<RecentCommentResponse> recentComments() {
        String sql = """
            SELECT c.id, c.content, u.email
            FROM comments c
            JOIN users u ON u.id = c.user_id
            ORDER BY c.created_at DESC
            LIMIT 5
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new RecentCommentResponse(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getString("email")
                ));
    }
}
