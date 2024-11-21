package com.TripLikeMovie.backend.domain.credential.service;

import com.TripLikeMovie.backend.domain.credential.domain.RefreshTokenRedisEntity;
import com.TripLikeMovie.backend.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import com.TripLikeMovie.backend.domain.credential.presentation.dto.request.LoginRequest;
import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenAndRefreshTokenDto;
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
    private final RefreshTokenRedisEntityRepository refreshTokenRedisEntityRepository;
    private final MemberUtils memberUtils;
    private final PasswordEncoder encoder;

    public AccessTokenAndRefreshTokenDto login(LoginRequest loginRequest){

        Member member = memberUtils.getMemberByEmail(loginRequest.getEmail());

        validPassword(member.getHashedPassword(), loginRequest.getPassword());

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getId(), member.getRole());

        RefreshTokenRedisEntity refreshTokenRedisEntity = RefreshTokenRedisEntity.builder()
            .id(member.getId().toString())  // memberId를 id로 사용
            .refreshToken(refreshToken)  // 생성된 refreshToken 설정
            .refreshTokenTtl(jwtTokenProvider.getRefreshTokenTTlSecond())  // 1일(86400초) 설정
            .build();

        refreshTokenRedisEntityRepository.save(refreshTokenRedisEntity); // 여기에서 Redis 저장

        return AccessTokenAndRefreshTokenDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public void logoutMember() {
        Member member = memberUtils.getMemberFromSecurityContext();
        refreshTokenRedisEntityRepository.deleteById(member.getId().toString());
    }

    public AccessTokenAndRefreshTokenDto signUp(MemberSignUpRequest signUpRequest) {
        String encodedPassword = encoder.encode(signUpRequest.getPassword());

        Member member = Member.builder()
            .email(signUpRequest.getEmail())
            .hashedPassword(encodedPassword)
            .nickname(signUpRequest.getNickname())
            .role(Role.USER)
            .build();

        memberRepository.save(member);

        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getId(), member.getRole());
        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getRole());

        RefreshTokenRedisEntity refreshTokenRedisEntity = RefreshTokenRedisEntity.builder()
            .id(member.getId().toString())
            .refreshToken(refreshToken)
            .refreshTokenTtl(jwtTokenProvider.getRefreshTokenTTlSecond())
            .build();

        refreshTokenRedisEntityRepository.save(refreshTokenRedisEntity);

        return AccessTokenAndRefreshTokenDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
    private void validPassword(String hashedPassword, String password) {

        if(!encoder.matches(password, hashedPassword)) {
            throw PasswordNotMatchException.EXCEPTION;
        }
    }

}
