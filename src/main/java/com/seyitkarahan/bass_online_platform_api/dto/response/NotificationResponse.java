package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private String message;
    private boolean isSent;
    private LocalDateTime notifyAt;
}
