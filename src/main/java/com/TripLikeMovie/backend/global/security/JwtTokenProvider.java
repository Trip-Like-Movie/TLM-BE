package com.TripLikeMovie.backend.global.security;

import com.TripLikeMovie.backend.domain.member.domain.Role;
import com.TripLikeMovie.backend.global.error.exception.auth.ExpiredTokenException;
import com.TripLikeMovie.backend.global.error.exception.auth.InvalidTokenException;
import com.TripLikeMovie.backend.global.error.exception.auth.RefreshTokenExpiredException;
import com.TripLikeMovie.backend.global.property.JwtProperties;
import com.TripLikeMovie.backend.global.security.auth.AuthDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final String ROLE = "role";
    private final String TYPE = "type";
    private final String ISSUER = "issuer";
    private final String ISSUER_VALUE = "TripLikeMovie";
    private final String ID = "id";
    private final String REFRESH_TOKEN = "refresh-token";


    public String generateAccessToken(Integer id, Role role) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn =
            new Date(issuedAt.getTime() + jwtProperties.getAccessExp() * 1000);
        return createAccessToken(id, issuedAt, role, accessTokenExpiresIn);
    }

    public String generateRefreshToken(Integer id, Role role) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn =
            new Date(issuedAt.getTime() + jwtProperties.getRefreshExp() * 1000);
        return createRefreshToken(id, issuedAt, role, refreshTokenExpiresIn);
    }

    public String resolveToken(HttpServletRequest request) {
        String rawHeader = request.getHeader(jwtProperties.getHeader());

        if (rawHeader != null
            && rawHeader.length() > jwtProperties.getPrefix().length()
            && rawHeader.startsWith(jwtProperties.getPrefix())) {
            return rawHeader.substring(jwtProperties.getPrefix().length());
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        String id = getClaims(token).getSubject();
        String role = (String) getClaims(token).get(ROLE);
        UserDetails userDetails = new AuthDetails(id, role);
        return new UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.getAuthorities());
    }

    public Long getRefreshTokenTTlSecond() {
        return jwtProperties.getRefreshExp();
    }

    public boolean isRefreshToken(String token) {
        return getClaims(token).get(TYPE).equals(REFRESH_TOKEN);
    }

    public Long parseRefreshToken(String token) {
        try {
            if (isRefreshToken(token)) {
                Claims claims = getClaims(token);
                return Long.parseLong(claims.getSubject());
            }
        } catch (ExpiredTokenException e) {
            throw RefreshTokenExpiredException.EXCEPTION;
        }
        throw InvalidTokenException.EXCEPTION;
    }


    private String createAccessToken(Integer id, Date issuedAt, Role role, Date tokenExpiresIn) {

        String ACCESS_TOKEN = "access-token";
        return Jwts.builder()
            .claim(ISSUER, ISSUER_VALUE)
            .claim(ID, id.toString())
            .claim(TYPE, ACCESS_TOKEN)
            .claim(ROLE, role.getValue())
            .issuedAt(issuedAt)
            .expiration(tokenExpiresIn)
            .signWith(getSecretKey())
            .compact();
    }

    private String createRefreshToken(Integer id, Date issuedAt, Role role, Date tokenExpiresIn) {

        return Jwts.builder()
            .claim(ISSUER, ISSUER_VALUE)
            .claim(ID, id.toString())
            .claim(TYPE, REFRESH_TOKEN)
            .claim(ROLE, role.getValue())
            .issuedAt(issuedAt)
            .expiration(tokenExpiresIn)
            .signWith(getSecretKey())
            .compact();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaims(String token) {
        try {
            String secret = jwtProperties.getSecretKey();
            return Jwts.parser()
                .verifyWith(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                    Jwts.SIG.HS256.key().build().getAlgorithm()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException e) {
            // 토큰이 유효하지 않거나 파싱할 수 없을 때 예외 발생
            throw InvalidTokenException.EXCEPTION;
        }
    }

}
