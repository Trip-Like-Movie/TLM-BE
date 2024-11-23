package com.TripLikeMovie.backend.domain.post.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAllPost {

    private Integer id;
    private String movieTitle;
    private Integer commentsSize;
    private String firstImageUrl;
    private Integer likeCount;
}
