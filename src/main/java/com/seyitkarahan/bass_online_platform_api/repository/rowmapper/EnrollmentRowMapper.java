package com.seyitkarahan.bass_online_platform_api.repository.rowmapper;

import com.seyitkarahan.bass_online_platform_api.dto.EnrollmentDto;
import com.seyitkarahan.bass_online_platform_api.entity.Enrollment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EnrollmentRowMapper implements RowMapper<Enrollment> {

    @Override
    public Enrollment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Enrollment.builder()
                .id(rs.getLong("id"))
                .studentId(rs.getLong("student_id"))
                .courseId(rs.getLong("course_id"))
                .enrollmentDate(
                        rs.getTimestamp("enrollment_date").toLocalDateTime()
                )
                .active(rs.getBoolean("active"))
                .build();
    }
}
