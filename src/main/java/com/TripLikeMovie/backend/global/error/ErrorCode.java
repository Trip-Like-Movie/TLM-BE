package com.TripLikeMovie.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_NOT_VERIFIED(400, "인증되지 않은 이메일입니다."),
    INVALID_VERIFICATION_CODE(400, "유효하지 않은 인증번호입니다"),
    DUPLICATED_EMAIL(400, "중복된 이메일입니다."),
    DUPLICATED_NICKNAME(400, "중복된 닉네임입니다."),
    FILE_EXCEPTION(400, "파일이 비어 있습니다"),
    BAD_FILE_EXTENSION(400, "확장자가 잘못 되었습니다."),

    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(401, "RefreshToken expired"),
    PASSWORD_NOT_MATCH(401, "비밀번호가 일치하지 않습니다"),

    MEMBER_NOT_FOUND(404 , "사용자를 찾을 수 없습니다." ),

    METHOD_NOT_ALLOWED(405,"해당 메서드를 지원하지 않습니다.");


    private final int status;
    private final String message;
}
