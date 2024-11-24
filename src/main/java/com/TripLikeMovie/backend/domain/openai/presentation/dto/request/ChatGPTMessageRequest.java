package com.TripLikeMovie.backend.domain.openai.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatGPTMessageRequest {
    private String type;
    private String prompt;
}
