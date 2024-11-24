package com.TripLikeMovie.backend.domain.openai.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.openai.domain.ConversationHistory;
import com.TripLikeMovie.backend.domain.openai.domain.repository.ConversationHistoryRepository;
import com.TripLikeMovie.backend.domain.openai.presentation.dto.request.ChatGPTRequest;
import com.TripLikeMovie.backend.domain.openai.presentation.dto.response.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGPTServiceImpl implements ChatGPTService {

    private final RestTemplate restTemplate;
    private final ConversationHistoryRepository conversationHistoryRepository;
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private static final int MAX_CONVERSATIONS = 10;

    @Override
    public void saveConversation(Member member, String message) {
        List<ConversationHistory> conversations = conversationHistoryRepository.findByMemberId(member.getId());

        // 최대 대화 수를 초과한 경우 가장 오래된 대화 삭제
        if (conversations.size() >= MAX_CONVERSATIONS) {
            ConversationHistory oldest = conversations.get(0);
            conversationHistoryRepository.deleteById(oldest.getId());
        }

        // 새로운 대화 저장
        ConversationHistory conversationHistory = new ConversationHistory(member, message);
        conversationHistoryRepository.save(conversationHistory);
        member.getConversationHistories().add(conversationHistory);
    }



    // ChatGPT API 호출
    public String getChatGPTResponse(Member member, String prompt) {
        String fullPrompt = buildFullPrompt(member, prompt);

        // ChatGPTRequest에 프롬프트 사용
        ChatGPTRequest request = new ChatGPTRequest(model, fullPrompt);

        // ChatGPT API 호출
        ChatGPTResponse chatGPTResponse = restTemplate.postForObject(apiURL, request, ChatGPTResponse.class);

        // 예외 처리 및 값이 없을 경우 처리
        if (chatGPTResponse == null || chatGPTResponse.getChoices().isEmpty()) {
            return "에러";
        }

        // 응답 선택지 반환
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

    private String buildFullPrompt(Member member, String prompt) {
        List<ConversationHistory> conversations = conversationHistoryRepository.findByMemberId(
            member.getId());
        StringBuilder sb = new StringBuilder();

        // 최근 최대 10개 대화만 포함
        for (int i = Math.max(conversations.size() - 10, 0); i < conversations.size(); i++) {
            sb.append(conversations.get(i).getMessage()).append("\n");
        }

        sb.append("User: ").append(prompt);
        return sb.toString();
    }
}
