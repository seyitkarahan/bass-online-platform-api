package com.seyitkarahan.bass_online_platform_api.controller;

import com.seyitkarahan.bass_online_platform_api.dto.response.CommentResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.CourseResponse;
import com.seyitkarahan.bass_online_platform_api.dto.response.UserResponse;
import com.seyitkarahan.bass_online_platform_api.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/users/{id}/role")
    public void updateUserRole(
            @PathVariable Long id,
            @RequestParam String role
    ) {
        adminService.updateUserRole(id, role);
    }

    @GetMapping("/courses")
    public List<CourseResponse> getAllCourses() {
        return adminService.getAllCourses();
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Long id) {
        adminService.deleteCourse(id);
    }

    @GetMapping("/comments")
    public List<CommentResponse> getAllComments() {
        return adminService.getAllComments();
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        adminService.deleteComment(id);
    }
}
