package com.TripLikeMovie.backend.domain.like.service;

import com.TripLikeMovie.backend.domain.like.domain.Like;
import com.TripLikeMovie.backend.domain.like.domain.repository.LikeRepository;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.global.error.exception.like.AlreadyLikedException;
import com.TripLikeMovie.backend.global.error.exception.like.NotLikedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public void like(Member member, Post post) {
        likeRepository.findByMemberIdAndPostId(member.getId(), post.getId())
            .ifPresent(like -> {
                throw AlreadyLikedException.EXCEPTION;
            });
        Like like = new Like(member, post);
        likeRepository.save(like);
        post.getLikes().add(like);
    }

    @Override
    public void dislike(Member member, Post post) {
        Like like = likeRepository.findByMemberIdAndPostId(member.getId(), post.getId())
            .orElseThrow(() -> NotLikedException.EXCEPTION);
        likeRepository.delete(like);
        post.getLikes().remove(like);
    }



}
