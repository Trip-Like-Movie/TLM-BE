package com.TripLikeMovie.backend.domain.comment.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentVo {

    private Integer id;
    private String content;

    private Integer authorId;
    private String authorNickname;
    private String authorImageUrl;

}
