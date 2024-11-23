package com.TripLikeMovie.backend.domain.post.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WritePostRequest {

    Integer movieId;
    String content;
    String locationName;
    String locationAddress;
}
