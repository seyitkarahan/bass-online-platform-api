package com.seyitkarahan.bass_online_platform_api.dto.response;

public record RecentCommentResponse(
        Long id,
        String content,
        String userEmail
) {
}
