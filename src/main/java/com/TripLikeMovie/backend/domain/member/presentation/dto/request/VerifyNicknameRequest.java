package com.TripLikeMovie.backend.domain.member.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyNicknameRequest {

    @Size(min = 2, max = 20, message = "별명은 2글자에서 20글자까지 가능합니다.")
    private String nickname;
}
