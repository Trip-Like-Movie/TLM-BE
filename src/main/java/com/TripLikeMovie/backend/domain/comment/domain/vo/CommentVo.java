package com.TripLikeMovie.backend.domain.comment.domain.vo;

import java.time.LocalDateTime;
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
