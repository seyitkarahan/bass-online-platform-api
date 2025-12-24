package com.seyitkarahan.bass_online_platform_api.dto.request;

import com.seyitkarahan.bass_online_platform_api.dto.QuestionAnswerDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmitRequest {

    private Long quizId;
    private List<QuestionAnswerDto> answers;
}
