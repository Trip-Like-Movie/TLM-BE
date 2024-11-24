package com.TripLikeMovie.backend.domain.openai.domain.repository;

import com.TripLikeMovie.backend.domain.openai.domain.ConversationHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Long> {

    List<ConversationHistory> findByMemberId(Integer memberId);
}
