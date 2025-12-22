package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Long cartId;
    private List<CourseResponse> courses;
    private BigDecimal totalPrice;
}
