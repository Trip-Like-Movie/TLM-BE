package com.TripLikeMovie.backend.domain.like.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.domain.Post;

public interface LikeService {

    void like(Member member, Post post);

    void dislike(Member member, Post post);
}
