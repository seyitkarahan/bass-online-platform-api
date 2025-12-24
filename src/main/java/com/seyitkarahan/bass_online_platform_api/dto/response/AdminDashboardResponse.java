package com.seyitkarahan.bass_online_platform_api.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record AdminDashboardResponse(
        long totalUsers,
        long totalStudents,
        long totalInstructors,
        long totalCourses,
        long activeEnrollments,
        BigDecimal totalRevenue,
        double averageRating,
        List<RecentCourseResponse> recentCourses,
        List<RecentCommentResponse> recentComments
) {
}
