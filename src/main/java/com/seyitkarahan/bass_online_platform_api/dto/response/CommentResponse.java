package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}
