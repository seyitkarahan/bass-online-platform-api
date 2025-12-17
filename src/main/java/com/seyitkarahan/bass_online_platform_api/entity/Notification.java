package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    private Long id;
    private Long userId;

    private String message;

    private LocalDateTime notifyAt;
    private boolean isSent;
}
