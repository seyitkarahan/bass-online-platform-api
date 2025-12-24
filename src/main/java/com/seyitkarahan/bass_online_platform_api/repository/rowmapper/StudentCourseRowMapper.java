package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.dto.response.StudentCourseResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentCourseRowMapper
        implements RowMapper<StudentCourseResponse> {

    @Override
    public StudentCourseResponse mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        return new StudentCourseResponse(
                rs.getLong("course_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getString("category_name"),
                rs.getTimestamp("enrollment_date").toLocalDateTime()
        );
    }
}
