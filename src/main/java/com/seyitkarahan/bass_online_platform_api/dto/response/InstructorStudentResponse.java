package com.seyitkarahan.bass_online_platform_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorStudentResponse {

    private Long studentId;
    private String studentName;
    private String studentEmail;

    private LocalDateTime enrolledAt;
}
