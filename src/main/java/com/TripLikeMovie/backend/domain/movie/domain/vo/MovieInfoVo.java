package com.TripLikeMovie.backend.domain.movie.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieInfoVo {

    private final Integer id;
    private final String title;
    private final String imageUrl;
}
