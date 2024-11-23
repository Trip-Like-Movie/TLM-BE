package com.TripLikeMovie.backend.domain.like.domain.repository;

import com.TripLikeMovie.backend.domain.like.domain.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberIdAndPostId(Integer memberId, Integer postId);

}