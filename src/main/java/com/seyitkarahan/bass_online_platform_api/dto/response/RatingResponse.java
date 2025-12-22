package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponse {
    private int rating;
    private String comment;
    private String studentName;
}
