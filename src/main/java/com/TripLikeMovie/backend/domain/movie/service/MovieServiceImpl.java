package com.TripLikeMovie.backend.domain.movie.service;

import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import com.TripLikeMovie.backend.domain.movie.domain.repository.MovieRepository;
import com.TripLikeMovie.backend.global.error.exception.movie.DuplicatedMovieTitleException;
import com.TripLikeMovie.backend.global.error.exception.movie.NotFoundMovieException;
import com.TripLikeMovie.backend.global.utils.image.ImageUtils;
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
}
