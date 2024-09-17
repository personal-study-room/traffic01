package com.onion.user.service.token;


import com.onion.common.jwt.JwtProvider;
import com.onion.user.dto.token.TokenDTO;
import com.onion.user.entity.RefreshTokenEntity;
import com.onion.user.entity.UserEntity;
import com.onion.user.mapper.RefreshTokenMapper;
import com.onion.user.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Transactional
@Service
@RequiredArgsConstructor
public class TokenCommandService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public TokenDTO createToken(UserEntity loginUser) {

        String accessTokenString = jwtProvider.createAccessToken(loginUser);

        if (loginUser.getRefreshToken() == null) {
            String refreshTokenString = jwtProvider.createRefreshToken(loginUser);
            RefreshTokenEntity refreshToken = RefreshTokenMapper.toEntity(refreshTokenString, loginUser);
            RefreshTokenEntity savedRefreshToken = refreshTokenRepository.save(refreshToken);
            loginUser.setRefreshToken(savedRefreshToken);
        }

        return RefreshTokenMapper.toTokenDTO(accessTokenString);
    }
}
