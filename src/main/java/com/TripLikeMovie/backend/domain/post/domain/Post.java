package com.TripLikeMovie.backend.domain.post.domain;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.vo.MemberInfoVo;
import com.TripLikeMovie.backend.domain.movie.domain.Movie;
import com.TripLikeMovie.backend.domain.movie.domain.vo.MovieInfoVo;
import com.TripLikeMovie.backend.domain.post.domain.vo.PostInfoVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name="member_id")
    @JsonIgnore
    private Member member;

    @ElementCollection
    private final List<String> imageUrls = new ArrayList<>();

    private String locationAddress;

    private String locationName;

    @ManyToOne
    @JoinColumn(name="movie_id")
    @JsonIgnore
    private Movie movie;

    public Post(Member member, Movie movie, String content, String locationName,
        String locationAddress) {
        this.content = content;
        this.member = member;
        this.movie = movie;
        this.locationName = locationName;
        this.locationAddress = locationAddress;

        if (movie != null) {
            movie.getPosts().add(this);
        }
        if (member != null) {
            member.getPosts().add(this);
        }
    }

    public void addFilePath(String imageUrl) {
        imageUrls.add(imageUrl);
    }

    public PostInfoVo getPostInfoVo() {

        MemberInfoVo memberInfo = member.getMemberInfo();
        MovieInfoVo movieInfo = movie.getMovieInfo();

        return PostInfoVo.builder()
            .content(content)
            .id(id)
            .locationAddress(locationAddress)
            .locationName(locationName)
            .imageUrls(imageUrls.stream()
                .map(imageUrl -> imageUrl.substring(imageUrl.lastIndexOf("TLM-BE/") + 7))
                .collect(Collectors.toList()))
            .movieImageUrl(movieInfo.getImageUrl())
            .movieId(movieInfo.getId())
            .movieTitle(movie.getTitle())
            .authorId(memberInfo.getId())
            .authorNickname(memberInfo.getNickname())
            .authorImageUrl(memberInfo.getImageUrl())
            .build();
    }

    public void update(String content, String locationName, String locationAddress) {
        this.content = content;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }
}
