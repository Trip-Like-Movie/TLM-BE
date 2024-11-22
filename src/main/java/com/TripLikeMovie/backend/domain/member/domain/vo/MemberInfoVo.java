package com.TripLikeMovie.backend.domain.member.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoVo {

    private final Integer id;
    private final String email;
    private final String nickname;
    private final String profileImageUrl;
}
