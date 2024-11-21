package com.TripLikeMovie.backend.domain.image.domain.repository;

import com.TripLikeMovie.backend.domain.image.domain.ProfileImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Integer> {
    Optional<ProfileImage> findByMemberId(Integer memberId);
}
