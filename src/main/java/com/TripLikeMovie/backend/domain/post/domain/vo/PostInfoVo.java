package com.TripLikeMovie.backend.domain.post.domain.vo;

import com.TripLikeMovie.backend.domain.member.domain.vo.MemberInfoVo;
import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
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

    private MovieInfoVo movieInfo;

    private MemberInfoVo memberInfo;
}
