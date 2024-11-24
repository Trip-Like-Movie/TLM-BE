package com.TripLikeMovie.backend.domain.movie.service;


import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.AllPostResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {

    Movie findById(Integer movieId);

    void duplicateTitle(String title);

    void createMovie(String title, MultipartFile filePath);

    void updateMoviePoster(Integer movieId, MultipartFile moviePoster);

    List<MovieInfoVo> findByTitle(String title);

    List<MovieInfoVo> rankingMovies();

    List<AllPostResponse> getAllPosts(Integer movieId);
}
