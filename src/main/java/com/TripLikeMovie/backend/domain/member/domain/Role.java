package com.TripLikeMovie.backend.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    USER("USER"),
    ADMIN("ADMIN");

    private final String value;
}
