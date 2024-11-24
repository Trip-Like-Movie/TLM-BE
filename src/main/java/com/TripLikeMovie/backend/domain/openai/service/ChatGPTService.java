package com.TripLikeMovie.backend.domain.openai.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;

public interface ChatGPTService {

    void saveConversation(Member member, String message);

    String getChatGPTResponse(Member member, String prompt);

}
