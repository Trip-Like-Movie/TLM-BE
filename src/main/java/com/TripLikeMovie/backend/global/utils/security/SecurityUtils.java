package com.TripLikeMovie.backend.global.utils.security;

import com.TripLikeMovie.backend.global.error.exception.member.MemberNotFoundException;
import com.TripLikeMovie.backend.global.security.auth.AuthDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {
    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
            authentication instanceof AnonymousAuthenticationToken) {
            throw MemberNotFoundException.EXCEPTION; // 익명 유저일 경우 예외 던지기
        }
        AuthDetails authDetails = (AuthDetails)authentication.getPrincipal();
        return Integer.valueOf(authDetails.getUsername());
    }
}
