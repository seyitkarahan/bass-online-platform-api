package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.entity.Rating;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RatingRowMapper implements RowMapper<Rating> {

    @Override
    public Rating mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        return Rating.builder()
                .id(rs.getLong("id"))
                .studentId(rs.getLong("student_id"))
                .courseId(rs.getLong("course_id"))
                .rating(rs.getInt("rating"))
                .comment(rs.getString("comment"))
                .createdAt(
                        rs.getTimestamp("created_at").toLocalDateTime()
                )
                .build();
    }
}
