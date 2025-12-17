package com.seyitkarahan.bass_online_platform_api.entity;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instructor {

    private Long id;
    private Long userId;
    private String bio;
    private String expertise;
}

