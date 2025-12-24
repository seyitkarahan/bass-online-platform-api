package com.seyitkarahan.bass_online_platform_api.service;

import com.seyitkarahan.bass_online_platform_api.dto.response.AdminDashboardResponse;
import com.seyitkarahan.bass_online_platform_api.repository.AdminDashboardRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final AdminDashboardRepository dashboardRepository;

    public AdminDashboardService(AdminDashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public AdminDashboardResponse getDashboard() {
        return new AdminDashboardResponse(
                dashboardRepository.countUsers(),
                dashboardRepository.countUsersByRole("STUDENT"),
                dashboardRepository.countUsersByRole("INSTRUCTOR"),
                dashboardRepository.countCourses(),
                dashboardRepository.countActiveEnrollments(),
                dashboardRepository.totalRevenue(),
                dashboardRepository.averageRating(),
                dashboardRepository.recentCourses(),
                dashboardRepository.recentComments()
        );
    }
}
