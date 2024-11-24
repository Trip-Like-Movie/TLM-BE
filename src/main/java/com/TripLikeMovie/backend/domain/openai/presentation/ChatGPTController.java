package com.TripLikeMovie.backend.domain.openai.presentation;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.openai.presentation.dto.response.MessageResponse;
import com.TripLikeMovie.backend.domain.openai.service.ChatGPTService;
import com.TripLikeMovie.backend.domain.openai.presentation.dto.request.ChatGPTMessageRequest;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatgpt")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;
    private final MemberUtils memberUtils;

    // 대화 시작
    @PostMapping
    public MessageResponse chat(@RequestBody ChatGPTMessageRequest chatGPTMessageRequest) {

        Member member = memberUtils.getMemberFromSecurityContext();

        // 영화 프롬프트 생성
        String prompt;
        if ("영화".equals(chatGPTMessageRequest.getType())) {
            prompt = getMoviePrompt(chatGPTMessageRequest.getPrompt());
        } else {
            prompt = chatGPTMessageRequest.getPrompt();
        }

        // 사용자의 메시지 저장
        chatGPTService.saveConversation(member, "User: " + prompt);

        // ChatGPT 응답 받기
        String result = chatGPTService.getChatGPTResponse(member, prompt);

        // GPT의 응답 저장
        chatGPTService.saveConversation(member, "ChatGPT: " + result);

        log.info("ChatGPTResponse: {}", result);
        result = result.replaceAll("(?i)^(ChatGPT:|Assistant:)", "").trim();  // "ChatGPT:" 또는 "Assistant:" 제거

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(result);
        messageResponse.setTimestamp(LocalDateTime.now());

        return messageResponse;
    }

    // 영화 프롬프트 생성
    private String getMoviePrompt(String movieName) {
        return "영화 '" + movieName + "'의 촬영지 3곳과 그에 해당하는 관광지명과 주소를 알려주세요. 가능하다면, 그 장소에서 어떠한 장면을 찍었는지도 알려주세요.";
    }
}
