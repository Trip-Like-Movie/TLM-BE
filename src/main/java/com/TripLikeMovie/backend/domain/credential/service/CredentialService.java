package com.TripLikeMovie.backend.domain.credential.service;

import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenAndRefreshTokenDto;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;

public interface CredentialService {

    AccessTokenAndRefreshTokenDto login(Integer memberId);

    void logoutMember();

    AccessTokenAndRefreshTokenDto signUp(MemberSignUpRequest signUpRequest);
}
