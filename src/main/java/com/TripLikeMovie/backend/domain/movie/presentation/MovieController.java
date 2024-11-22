package com.TripLikeMovie.backend.domain.movie.presentation;

import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.movie.presentation.dto.request.CreateMovieRequest;
import com.TripLikeMovie.backend.domain.movie.service.MovieService;
import com.TripLikeMovie.backend.global.utils.image.ImageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    private final ObjectMapper objectMapper;

    private final ImageUtils imageUtils;

    @GetMapping("/{movieId}")
    public MovieInfoVo getMovie(@PathVariable Integer movieId) {
        return movieService.findById(movieId).getMovieInfo();
    }

    @PostMapping(consumes = "multipart/form-data")
    public void createMovie(
        @RequestPart("movieData") String movieData,
        @RequestPart("moviePoster") MultipartFile moviePoster
    ) throws JsonProcessingException {
        CreateMovieRequest createMovieRequest = objectMapper.readValue(movieData,
            CreateMovieRequest.class);

        movieService.duplicateTitle(createMovieRequest.getTitle());
        movieService.createMovie(createMovieRequest.getTitle(), moviePoster);

    }

    @PatchMapping(value = "/{movieId}", consumes = "multipart/form-data")
    public void updateMovieImage(
        @PathVariable Integer movieId,
        @RequestPart("moviePoster") MultipartFile moviePoster
    ) {
        movieService.updateMoviePoster(movieId, moviePoster);
    }

    @GetMapping
    public List<MovieInfoVo> getMovies(@RequestParam(required = false) String title) {
        return movieService.findByTitle(title);
    }

}
