package com.TripLikeMovie.backend.domain.post.domain.repository;

import com.TripLikeMovie.backend.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
