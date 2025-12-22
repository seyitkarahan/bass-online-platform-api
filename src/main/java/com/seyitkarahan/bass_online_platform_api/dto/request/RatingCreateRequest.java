package com.seyitkarahan.bass_online_platform_api.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingCreateRequest {
    private int rating;
    private String comment;
}
