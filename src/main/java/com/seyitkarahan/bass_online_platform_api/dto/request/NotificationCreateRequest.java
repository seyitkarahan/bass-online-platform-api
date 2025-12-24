package com.seyitkarahan.bass_online_platform_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreateRequest {

    private Long userId;
    private String message;
    private LocalDateTime notifyAt;
}
