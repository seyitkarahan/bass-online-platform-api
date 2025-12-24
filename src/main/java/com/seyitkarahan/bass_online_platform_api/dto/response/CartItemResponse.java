package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private Long cartItemId;
    private Long courseId;
    private String courseTitle;
    private double price;
}
