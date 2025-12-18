package com.seyitkarahan.bass_online_platform_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private String message;
    private boolean isSent;
    private LocalDateTime notifyAt;
}
