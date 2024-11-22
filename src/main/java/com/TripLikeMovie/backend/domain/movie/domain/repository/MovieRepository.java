package com.TripLikeMovie.backend.domain.movie.domain.repository;

import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByTitle(String title);
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
