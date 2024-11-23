package com.TripLikeMovie.backend.domain.like.presentation;

import com.TripLikeMovie.backend.domain.like.service.LikeService;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.domain.post.service.PostService;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/like")
public class LikeController {

    private final LikeService likeService;
    private final MemberUtils memberUtils;
    private final PostService postService;


    @PostMapping
    public void likePost(@PathVariable("postId") Integer postId) {
        Member member = memberUtils.getMemberFromSecurityContext();
        Post post = postService.findById(postId);

        likeService.like(member, post);
    }

    @DeleteMapping
    public void dislikePost(@PathVariable("postId") Integer postId) {
        Member member = memberUtils.getMemberFromSecurityContext();
        Post post = postService.findById(postId);

        likeService.dislike(member, post);
    }

}
