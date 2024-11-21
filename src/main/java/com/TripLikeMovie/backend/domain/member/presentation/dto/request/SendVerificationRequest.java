package com.TripLikeMovie.backend.domain.member.presentation.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendVerificationRequest {

    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
}
