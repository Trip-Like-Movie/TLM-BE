package com.TripLikeMovie.backend.domain.movie.domain;

import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String imageUrl;

    public Movie(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public MovieInfoVo getMovieInfo() {
        return MovieInfoVo.builder()
            .id(id)
            .title(title)
            .imageUrl(imageUrl)
            .build();
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
