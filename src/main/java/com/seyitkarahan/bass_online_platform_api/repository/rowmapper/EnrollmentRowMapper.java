package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.dto.EnrollmentDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EnrollmentRowMapper implements RowMapper<EnrollmentDto> {

    @Override
    public EnrollmentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EnrollmentDto(
                rs.getLong("enrollment_id"),
                rs.getString("course_title"),
                rs.getBoolean("active"),
                rs.getString("payment_status")
        );
    }
}

