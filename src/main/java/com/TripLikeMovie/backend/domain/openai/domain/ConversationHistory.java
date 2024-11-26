package com.TripLikeMovie.backend.domain.openai.domain;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "conversation_histories")
@NoArgsConstructor
public class ConversationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String message;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public ConversationHistory(Member member, String message) {
        this.member = member;
        this.message = message;
    }
}
