package com.TripLikeMovie.backend.domain.credential.service;

import com.TripLikeMovie.backend.domain.credential.presentation.dto.request.LoginRequest;
import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenAndRefreshTokenDto;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;

public interface CredentialService {

    AccessTokenAndRefreshTokenDto login(LoginRequest loginRequest);

    void logoutMember();

    AccessTokenAndRefreshTokenDto signUp(MemberSignUpRequest signUpRequest);
}
