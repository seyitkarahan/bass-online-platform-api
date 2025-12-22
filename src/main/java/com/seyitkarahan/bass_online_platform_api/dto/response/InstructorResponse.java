package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorResponse {
    private Long id;
    private String name;
    private String bio;
    private String expertise;
}
