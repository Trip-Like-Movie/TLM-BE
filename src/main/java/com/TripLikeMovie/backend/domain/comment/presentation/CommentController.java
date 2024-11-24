package com.TripLikeMovie.backend.domain.comment.presentation;

import com.TripLikeMovie.backend.domain.comment.presentation.dto.request.WriteCommentRequest;
import com.TripLikeMovie.backend.domain.comment.service.CommentService;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.domain.post.service.PostService;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final MemberUtils memberUtils;
    private final PostService postService;

    @PostMapping
    public void createComment(@PathVariable Integer postId,
        @RequestBody WriteCommentRequest writeCommentRequest) {

        Member member = memberUtils.getMemberFromSecurityContext();
        Post post = postService.findById(postId);
        commentService.writeComment(member, post, writeCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {

        Member member = memberUtils.getMemberFromSecurityContext();
        Post post = postService.findById(postId);
        commentService.deleteComment(member, post, commentId);
    }
}
