package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.dto.response.NotificationResponse;
import com.seyitkarahan.bass_online_platform_api.entity.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class NotificationRepository {

    private final JdbcTemplate jdbcTemplate;

    public NotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long userId, String message, LocalDateTime notifyAt) {
        String sql = """
            INSERT INTO notifications (user_id, message, notify_at)
            VALUES (?, ?, ?)
        """;

        jdbcTemplate.update(sql, userId, message, notifyAt);
    }

    public List<NotificationResponse> findByUser(Long userId) {
        String sql = """
            SELECT id, message, notify_at, is_sent
            FROM notifications
            WHERE user_id = ?
            ORDER BY notify_at DESC
        """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new NotificationResponse(
                        rs.getLong("id"),
                        rs.getString("message"),
                        rs.getBoolean("is_sent"), // ⬅️ önce boolean
                        rs.getTimestamp("notify_at") != null
                                ? rs.getTimestamp("notify_at").toLocalDateTime()
                                : null
                ),
                userId
        );

    }

    public void markAsSent(Long notificationId) {
        String sql = """
            UPDATE notifications
            SET is_sent = true
            WHERE id = ?
        """;

        jdbcTemplate.update(sql, notificationId);
    }
}
