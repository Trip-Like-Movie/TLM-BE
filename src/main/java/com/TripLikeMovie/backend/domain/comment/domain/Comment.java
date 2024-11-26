package com.TripLikeMovie.backend.domain.comment.domain;

import com.TripLikeMovie.backend.domain.comment.domain.vo.CommentVo;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.vo.MemberInfoVo;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.global.database.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment(Member member, Post post, String content) {
        this.member = member;
        this.post = post;
        this.content = content;

        this.post.getComments().add(this);
    }

    public CommentVo getCommentInfo() {
        MemberInfoVo memberInfo = member.getMemberInfo();

        return CommentVo.builder()
            .content(content)
            .id(id)
            .authorId(memberInfo.getId())
            .authorNickname(memberInfo.getNickname())
            .authorImageUrl(memberInfo.getImageUrl())
            .createdAt(getCreatedAt())
            .updatedAt(getUpdatedAt())
            .build();
    }

}
