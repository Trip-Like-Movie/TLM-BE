package com.TripLikeMovie.backend.domain.comment.service;

import com.TripLikeMovie.backend.domain.comment.presentation.dto.request.WriteCommentRequest;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.domain.Post;

public interface CommentService {

    void writeComment(Member member, Post post, WriteCommentRequest writeCommentRequest);

    void deleteComment(Member member, Post post, Integer commentId);
}
