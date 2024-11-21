package com.TripLikeMovie.backend.global.utils.member;

import com.TripLikeMovie.backend.domain.member.domain.Member;

public interface MemberUtils {

    Member getMemberByEmail(String email);

    Member getMemberFromSecurityContext();

}
