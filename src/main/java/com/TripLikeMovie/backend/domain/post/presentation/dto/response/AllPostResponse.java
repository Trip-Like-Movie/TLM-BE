package com.TripLikeMovie.backend.domain.post.presentation.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllPostResponse {
    private Integer id;
    private String firstImageUrl;

    private String movieTitle;
    private Integer MemberId;
    private String memberNickname;
    private String memberImageUrl;

    private Integer likedCount;
    private Integer commentsCount;
}
