package com.TripLikeMovie.backend.domain.member.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordVerifyEmailRequest {
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @Size(min = 2, max = 20, message = "별명은 2글자에서 20글자까지 가능합니다.")
    private String nickname;
}
