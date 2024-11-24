package com.TripLikeMovie.backend.domain.movie.service;

import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import com.TripLikeMovie.backend.domain.movie.domain.repository.MovieRepository;
import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.post.domain.vo.PostInfoVo;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.AllPostResponse;
import com.TripLikeMovie.backend.global.error.exception.movie.DuplicatedMovieTitleException;
import com.TripLikeMovie.backend.global.error.exception.movie.NotFoundMovieException;
import com.TripLikeMovie.backend.global.utils.image.ImageUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ImageUtils imageUtils;

    @Override
    @Transactional(readOnly = true)
    public Movie findById(Integer movieId) {
        return movieRepository.findById(movieId)
            .orElseThrow(() -> NotFoundMovieException.EXCEPTION);
    }

    @Override
    @Transactional(readOnly = true)
    public void duplicateTitle(String title) {
        movieRepository.findByTitle(title).ifPresent(movie -> {
            throw DuplicatedMovieTitleException.EXCEPTION;
        });
    }

    @Override
    public void createMovie(String title, MultipartFile multipartFile) {
        String filePath = imageUtils.saveImage(multipartFile, "movies/");
        Movie movie = new Movie(title, filePath);
        movieRepository.save(movie);
    }

    @Override
    public void updateMoviePoster(Integer movieId, MultipartFile moviePoster) {

        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> NotFoundMovieException.EXCEPTION);

        String imageUrl = movie.getImageUrl();

        imageUtils.deleteImage(imageUrl);

        String filePath = imageUtils.saveImage(moviePoster, "movies/");

        movie.updateImageUrl(filePath);
    }

    @Override
    public List<MovieInfoVo> findByTitle(String title) {
        // title이 null 또는 비어 있으면 모든 영화 반환
        if (title == null || title.trim().isEmpty()) {
            return movieRepository.findAll().stream()
                .map(Movie::getMovieInfo)
                .toList();
        }

        // title이 주어졌다면 검색
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
            .map(Movie::getMovieInfo)
            .toList();
    }

    @Override
    public List<MovieInfoVo> rankingMovies() {

        return movieRepository.findTop3Movies().stream()
            .map(Movie::getMovieInfo)
            .toList();
    }

    @Override
    public List<AllPostResponse> getAllPosts(Integer movieId) {
        // 영화 조회
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> NotFoundMovieException.EXCEPTION);


        return movie.getPosts().stream()
            .map(
                post -> {
                    PostInfoVo postInfoVo = post.getPostInfoVo();
                    // 각 게시물에 대해 AllPostResponse 객체 생성
                    AllPostResponse response = new AllPostResponse();
                    response.setId(postInfoVo.getId());
                    response.setFirstImageUrl(postInfoVo.getImageUrls().get(0));
                    response.setMovieTitle(movie.getTitle());
                    response.setMemberId(postInfoVo.getAuthorId());
                    response.setLikedCount(postInfoVo.getLikeCount());
                    response.setCommentsCount(postInfoVo.getComments().size());
                    response.setMemberNickname(postInfoVo.getAuthorNickname());
                    response.setMemberImageUrl(postInfoVo.getAuthorImageUrl());
                    return response;
                })
            .collect(Collectors.toList());  // List<AllPostResponse>로 반환
    }

}
