package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.entity.Course;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Course.builder()
                .id(rs.getLong("id"))
                .instructorId(rs.getLong("instructor_id"))
                .categoryId(rs.getLong("category_id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .price(rs.getBigDecimal("price"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
