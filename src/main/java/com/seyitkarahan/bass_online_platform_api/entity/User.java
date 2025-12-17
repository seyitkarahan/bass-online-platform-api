package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;

    private String role; // STUDENT, INSTRUCTOR, ADMIN

    private LocalDateTime createdAt;
}

