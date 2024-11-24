package com.TripLikeMovie.backend.domain.openai.presentation.dto.request;

import com.TripLikeMovie.backend.domain.openai.presentation.dto.response.Message;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ChatGPTRequest {
    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}