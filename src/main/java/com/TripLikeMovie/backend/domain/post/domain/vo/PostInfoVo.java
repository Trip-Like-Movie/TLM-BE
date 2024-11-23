package com.TripLikeMovie.backend.domain.post.domain.vo;

import com.TripLikeMovie.backend.domain.comment.domain.vo.CommentVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostInfoVo {

    private Integer id;
    private String content;
    private List<String> imageUrls;
    private String locationName;
    private String locationAddress;
    private List<CommentVo> comments;
    private Integer likeCount;

    private Integer authorId;
    private String authorNickname;
    private String authorImageUrl;

    private Integer movieId;
    private String movieTitle;
    private String movieImageUrl;

}
