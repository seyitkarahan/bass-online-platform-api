package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.dto.CourseListDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseListRowMapper implements RowMapper<CourseListDto> {

    @Override
    public CourseListDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CourseListDto(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getDouble("price"),
                rs.getString("instructor_name"),
                rs.getString("category_name")
        );
    }
}
