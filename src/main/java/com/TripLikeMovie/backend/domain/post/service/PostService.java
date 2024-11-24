package com.TripLikeMovie.backend.domain.post.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.domain.post.presentation.dto.request.UpdatePostRequest;
import com.TripLikeMovie.backend.domain.post.presentation.dto.request.WritePostRequest;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.AllPostResponse;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.DetailPostResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    void writePost(Member member, Movie movie, WritePostRequest postData, List<MultipartFile> images);

    Post findById(Integer postId);

    List<AllPostResponse> findAll();

    void update(Integer postId, UpdatePostRequest updatePostRequest);

    void deletePost(Integer postId);

    DetailPostResponse findByIdDetail(Integer postId);
}
