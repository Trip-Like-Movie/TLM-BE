package com.TripLikeMovie.backend.domain.member.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignUpRequest {

    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,16}$",
        message = "비밀번호는 숫자, 문자, 특수문자를 포함하고, 8자에서 16자 사이여야 합니다."
    )
    private String password;

    @Size(min = 2, max = 20, message = "별명은 2글자에서 20글자까지 가능합니다.")
    private String nickname;

}
