package com.TripLikeMovie.backend.domain.member.domain.vo;

import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoVo {

    private final Integer id;
    private final String email;
    private final String nickname;
    private final String imageUrl;
    private final String role;
    private final List<MovieInfoVo> movies; // 사용자와 관련된 영화 정보

}
