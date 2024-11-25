package com.TripLikeMovie.backend.domain.admin.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.Role;
import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final PostRepository postRepository;

    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {
            return null;
        }
        boolean matches = encoder.matches(password, member.getHashedPassword());
        if (!matches) {
            return null;
        }
        if (!member.getRole().equals(Role.ADMIN)) {
            return null;
        }
        return member;
    }


    @Transactional
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }
}
