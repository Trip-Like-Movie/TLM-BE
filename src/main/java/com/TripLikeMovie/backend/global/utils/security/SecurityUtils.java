package com.TripLikeMovie.backend.global.utils.security;

import com.TripLikeMovie.backend.global.error.exception.member.MemberNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw MemberNotFoundException.EXCEPTION;
        }
        return Integer.valueOf(authentication.getName());
    }
}
