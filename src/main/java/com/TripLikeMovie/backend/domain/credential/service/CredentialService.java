package com.TripLikeMovie.backend.domain.credential.service;

import com.TripLikeMovie.backend.domain.credential.presentation.dto.request.LoginRequest;
import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenDto;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;

public interface CredentialService {

    AccessTokenDto login(LoginRequest loginRequest);

    AccessTokenDto signUp(MemberSignUpRequest signUpRequest);
}
