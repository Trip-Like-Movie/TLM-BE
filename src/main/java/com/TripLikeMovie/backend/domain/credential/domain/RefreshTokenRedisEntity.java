package com.TripLikeMovie.backend.domain.credential.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken")
@Getter
public class RefreshTokenRedisEntity {

    @Id
    private final String id;

    @Indexed
    private final String refreshToken;

    // refresh token의 TTL을 1일로 설정 (86400초)
    @TimeToLive
    private final Long refreshTokenTtl;

    @Builder
    public RefreshTokenRedisEntity(String id, String refreshToken, Long refreshTokenTtl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.refreshTokenTtl = refreshTokenTtl != null ? refreshTokenTtl : 86400L; // 기본 TTL 1일 (초 단위)
    }
}
