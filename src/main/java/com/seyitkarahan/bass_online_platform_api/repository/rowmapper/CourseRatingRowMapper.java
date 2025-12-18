package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.dto.CourseRatingDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseRatingRowMapper implements RowMapper<CourseRatingDto> {

    @Override
    public CourseRatingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CourseRatingDto(
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getString("student_name")
        );
    }
}
