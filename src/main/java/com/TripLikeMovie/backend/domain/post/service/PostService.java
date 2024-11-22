package com.TripLikeMovie.backend.domain.post.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.post.presentation.dto.request.WritePostRequest;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    void writePost(Member member, WritePostRequest postData, List<MultipartFile> images);
}
