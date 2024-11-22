package com.TripLikeMovie.backend.domain.post.presentation;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.presentation.dto.request.WritePostRequest;
import com.TripLikeMovie.backend.domain.post.service.PostService;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberUtils memberUtils;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = "multipart/form-data")
    public void writePost(
        @RequestPart("postData") String postData,
        @RequestPart("images") List<MultipartFile> images
    ) throws JsonProcessingException {
        Member member = memberUtils.getMemberFromSecurityContext();

        WritePostRequest writePostRequest = objectMapper.readValue(postData,
            WritePostRequest.class);

        postService.writePost(member, writePostRequest, images);

    }

}
