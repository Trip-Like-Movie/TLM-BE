package com.TripLikeMovie.backend.domain.member.domain;

import com.TripLikeMovie.backend.domain.member.domain.vo.MemberInfoVo;
import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "members")
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String imageUrl;

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    public MemberInfoVo getMemberInfo() {

        String profileImageUrl = (imageUrl != null)
            ? imageUrl.substring(imageUrl.lastIndexOf("TLM-BE/") + 7)
            : null;  // 기본 이미지를 사용하지 않고 null을 반환

        // 중복 없는 영화 추출
        Set<MovieInfoVo> uniqueMovies = posts.stream()
            .map(Post::getMovie)
            .distinct()
            .map(movie -> MovieInfoVo.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .build())
            .collect(Collectors.toSet());

        // MemberInfoVo 생성
        return MemberInfoVo.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .imageUrl(profileImageUrl)
            .movies(uniqueMovies) // 추가된 영화 목록
            .build();
    }

    public void updateProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void changePassword(String encodedPassword) {
        this.hashedPassword = encodedPassword;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
