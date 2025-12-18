package com.seyitkarahan.bass_online_platform_api.repository;

import com.seyitkarahan.bass_online_platform_api.entity.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepository {

    private final JdbcTemplate jdbcTemplate;

    public NotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Notification> findPending() {
        String sql = "SELECT * FROM notifications WHERE is_sent = false";
        return jdbcTemplate.query(sql, (rs, i) ->
                Notification.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .message(rs.getString("message"))
                        .notifyAt(rs.getTimestamp("notify_at") != null
                                ? rs.getTimestamp("notify_at").toLocalDateTime()
                                : null)
                        .isSent(rs.getBoolean("is_sent"))
                        .build()
        );
    }

    public void markAsSent(Long id) {
        jdbcTemplate.update(
                "UPDATE notifications SET is_sent = true WHERE id = ?",
                id
        );
    }
}
