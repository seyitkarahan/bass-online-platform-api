package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.dto.CourseCommentDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseCommentRowMapper implements RowMapper<CourseCommentDto> {

    @Override
    public CourseCommentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CourseCommentDto(
                rs.getString("user_name"),
                rs.getString("content"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
