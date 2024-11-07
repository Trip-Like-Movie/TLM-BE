package com.TripLikeMovie.backend.domain.member.domain.repository;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository  extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String username);
}
