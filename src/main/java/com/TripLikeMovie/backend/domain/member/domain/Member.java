package com.TripLikeMovie.backend.domain.member.domain;

import com.TripLikeMovie.backend.domain.comment.domain.Comment;
import com.TripLikeMovie.backend.domain.like.domain.Like;
import com.TripLikeMovie.backend.domain.member.domain.vo.MemberInfoVo;
import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.openai.domain.ConversationHistory;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import jakarta.persistence.CascadeType;
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
import java.util.Comparator;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ConversationHistory> conversationHistories = new ArrayList<>();

    public MemberInfoVo getMemberInfo() {
        // imageUrl이 null일 경우 null을 반환
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
                .imageUrl(movie.getImageUrl()) // Movie의 imageUrl도 포함
                .build())
            .collect(Collectors.toSet());

        // 정렬: title 기준으로 정렬
        List<MovieInfoVo> sortedMovies = uniqueMovies.stream()
            .sorted(Comparator.comparing(MovieInfoVo::getTitle)) // title을 기준으로 정렬
            .collect(Collectors.toList());

        // MemberInfoVo 생성
        return MemberInfoVo.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .imageUrl(profileImageUrl) // imageUrl이 null일 경우 null 반환
            .movies(sortedMovies) // 정렬된 영화 목록
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
