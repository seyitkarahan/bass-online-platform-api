package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private Long studentId;
    private int rating;
    private String comment;
    private LocalDateTime createdDate;
}
