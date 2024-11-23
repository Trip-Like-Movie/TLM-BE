package com.TripLikeMovie.backend.domain.post.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.domain.post.domain.repository.PostRepository;
import com.TripLikeMovie.backend.domain.post.presentation.dto.request.WritePostRequest;
import com.TripLikeMovie.backend.global.error.exception.post.PostNotFoundException;
import com.TripLikeMovie.backend.global.utils.image.ImageUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageUtils imageUtils;

    @Override
    public void writePost(Member member, Movie movie, WritePostRequest postData,
        List<MultipartFile> images) {

        Post post = new Post(member, movie, postData.getContent(), postData.getLocationName(),
            postData.getLocationAddress());

        for (MultipartFile image : images) {
            String filePath = imageUtils.saveImage(image, "posts/");
            post.addFilePath(filePath);
        }
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findById(Integer postId) {
        return postRepository.findById(postId).orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }
}
