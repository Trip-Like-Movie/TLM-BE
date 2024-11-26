package com.TripLikeMovie.backend.domain.credential.service;

import com.TripLikeMovie.backend.domain.credential.presentation.dto.request.LoginRequest;
import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenDto;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.Role;
import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;
import com.TripLikeMovie.backend.global.error.exception.credential.PasswordNotMatchException;
import com.TripLikeMovie.backend.global.security.JwtTokenProvider;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberUtils memberUtils;
    private final PasswordEncoder encoder;

    public AccessTokenDto login(LoginRequest loginRequest){

        Member member = memberUtils.getMemberByEmail(loginRequest.getEmail());

        validPassword(member.getHashedPassword(), loginRequest.getPassword());

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getRole());



        return AccessTokenDto.builder()
            .accessToken(accessToken)
            .build();
    }

    public AccessTokenDto signUp(MemberSignUpRequest signUpRequest) {
        String encodedPassword = encoder.encode(signUpRequest.getPassword());

        Member member = Member.builder()
            .email(signUpRequest.getEmail())
            .hashedPassword(encodedPassword)
            .nickname(signUpRequest.getNickname())
            .role(Role.USER)
            .imageUrl("/Users/kimjongchan/TripLikeMovie/TLM-BE/uploads/profiles/image.png")
            .build();

        memberRepository.save(member);

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getRole());


        return AccessTokenDto.builder()
            .accessToken(accessToken)
            .build();
    }
    private void validPassword(String hashedPassword, String password) {

        if(!encoder.matches(password, hashedPassword)) {
            throw PasswordNotMatchException.EXCEPTION;
        }
    }

}
