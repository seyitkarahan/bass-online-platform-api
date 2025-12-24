package com.seyitkarahan.bass_online_platform_api.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswer {

    private Long id;
    private Long attemptId;
    private Long questionId;

    private String answer;
    private boolean correct;
}
