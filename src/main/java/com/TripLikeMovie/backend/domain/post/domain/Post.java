package com.TripLikeMovie.backend.domain.post.domain;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
    private Member member;

    @ElementCollection
    private final List<String> filePaths = new ArrayList<>();

    private String locationAddress;

    private String locationName;

    public Post(Member member, String content, String locationName, String locationAddress) {
        this.content = content;
        this.member = member;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }

    public void addFilePath(String filePath) {
        filePaths.add(filePath);
    }
}
