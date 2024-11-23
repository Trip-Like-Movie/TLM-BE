package com.TripLikeMovie.backend.domain.comment.service;

import com.TripLikeMovie.backend.domain.comment.domain.Comment;
import com.TripLikeMovie.backend.domain.comment.domain.repository.CommentRepository;
import com.TripLikeMovie.backend.domain.comment.presentation.dto.request.WriteCommentRequest;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.global.error.exception.comment.CommentNotFoundException;
import com.TripLikeMovie.backend.global.error.exception.comment.CommentNotMatchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void writeComment(Member member, Post post, WriteCommentRequest writeCommentRequest) {
        Comment comment = new Comment(member, post, writeCommentRequest.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Member member, Post post, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> CommentNotFoundException.EXCEPTION);

        if (!comment.getMember().equals(member)) {
            throw CommentNotMatchMemberException.EXCEPTION;
        }
        commentRepository.delete(comment);
    }
}
