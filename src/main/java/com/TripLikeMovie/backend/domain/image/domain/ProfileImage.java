package com.TripLikeMovie.backend.domain.image.domain;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "profile_images")
@Entity
@NoArgsConstructor
@Getter
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore // 순환 참조 방지
    private Member member;

    public ProfileImage(String imagePath, Member member) {
        this.imagePath = imagePath;
        this.member = member;
    }

}
