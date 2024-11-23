package com.TripLikeMovie.backend.domain.movie.domain;

import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "movie")
    private List<Post> posts = new ArrayList<>();

    public Movie(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public MovieInfoVo getMovieInfo() {
        return MovieInfoVo.builder()
            .id(id)
            .title(title)
            .imageUrl(imageUrl.substring(imageUrl.lastIndexOf("TLM-BE/") + 7))
            .build();
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
