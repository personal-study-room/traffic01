package com.onion.common.jwt;

import static com.onion.common.jwt.TokenType.ACCESS;
import static com.onion.common.util.OnionConstant.TOKEN_BEARER;
import static com.onion.common.util.OnionConstant.TOKEN_ID;
import static com.onion.common.util.OnionConstant.TOKEN_TYPE;

import com.onion.common.exception.CustomAuthenticationException;
import com.onion.user.entity.UserEntity;
import com.onion.user.service.token.JwtBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    //access token valid time - 10분
    @Value("${jwt.valid-time.access}")
    private long accessTokenValidTime;

    //refresh token valid time - 60분
    @Value("${jwt.valid-time.refresh}")
    private long refreshTokenValidTime;

    private Key secretKey;

    private final UserDetailsService userDetailsService;
    private final JwtBlacklistService jwtBlacklistService;


    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String createAccessToken(UserEntity user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(TOKEN_ID, user.getId());
        claims.put(TOKEN_TYPE, ACCESS);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String createRefreshToken(UserEntity user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(TOKEN_ID, user.getId());
        claims.put(TOKEN_TYPE, ACCESS);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String resolveToken(String bearerToken) {

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_BEARER)) {
            return bearerToken.substring(TOKEN_BEARER.length());
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
            throw new CustomAuthenticationException("Invalid JWT token", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token: {}", ex.getMessage());
            throw new CustomAuthenticationException("Expired JWT token", ex.getMessage());
        }
    }

    public Authentication getAuthentication(String token) {
        String username = Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    public boolean isTokenInBlacklist(String token) {

        return jwtBlacklistService.isTokenBlacklisted(token);
    }

    public LocalDateTime getExpiredTime(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ExpiredJwtException ex) {
            return ex.getClaims().getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new CustomAuthenticationException("Invalid JWT token", ex.getMessage());
        }
    }

    public UUID getUserId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(TOKEN_ID, UUID.class);
        } catch (ExpiredJwtException ex) {
            return ex.getClaims().get(TOKEN_ID, UUID.class);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new CustomAuthenticationException("Invalid JWT token", ex.getMessage());
        }

    }
}
