package com.TripLikeMovie.backend.domain.movie.domain.repository;

import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByTitle(String title);
    List<Movie> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT m FROM Movie m LEFT JOIN m.posts p GROUP BY m.id ORDER BY COUNT(p) DESC LIMIT 3")
    List<Movie> findTop3Movies();
}
