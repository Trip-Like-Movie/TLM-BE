package com.TripLikeMovie.backend.domain.credential.domain.repository;

import com.TripLikeMovie.backend.domain.credential.domain.RefreshTokenRedisEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisEntityRepository extends
    CrudRepository<RefreshTokenRedisEntity, String> {

    Optional<RefreshTokenRedisEntity> findByRefreshToken(String refreshToken);
}
