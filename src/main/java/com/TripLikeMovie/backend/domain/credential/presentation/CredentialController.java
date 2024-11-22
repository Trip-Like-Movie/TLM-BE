package com.TripLikeMovie.backend.domain.credential.presentation;

import com.TripLikeMovie.backend.domain.credential.presentation.dto.request.LoginRequest;
import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenDto;
import com.TripLikeMovie.backend.domain.credential.service.CredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
@RestController
public class CredentialController {

    private final CredentialService credentialService;

    @PostMapping("/login")
    public AccessTokenDto login(@RequestBody LoginRequest loginRequest) {
        return credentialService.login(loginRequest);
    }

}
