package com.TripLikeMovie.backend.domain.post.presentation.dto.response;

import com.TripLikeMovie.backend.domain.comment.domain.vo.CommentVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailPostResponse {

    private Integer id;
    private String content;
    private List<String> imageUrls;
    private String locationName;
    private String locationAddress;
    private List<CommentVo> comments;
    private Integer likeCount;
    private Boolean liked;

    private Integer authorId;
    private String authorNickname;
    private String authorImageUrl;

    private Integer movieId;
    private String movieTitle;
    private String movieImageUrl;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime updatedAt;

}
