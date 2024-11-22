package com.TripLikeMovie.backend.domain.movie.service;


import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {

    Movie findById(Integer movieId);

    void duplicateTitle(String title);

    void createMovie(String title, MultipartFile filePath);

    void updateMoviePoster(Integer movieId, MultipartFile moviePoster);
}
