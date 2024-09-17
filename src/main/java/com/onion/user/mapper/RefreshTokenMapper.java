package com.onion.user.mapper;

import com.onion.user.dto.token.RefreshTokenDTO;
import com.onion.user.dto.token.TokenDTO;
import com.onion.user.entity.RefreshTokenEntity;
import com.onion.user.entity.UserEntity;

public class RefreshTokenMapper {

    public static RefreshTokenEntity toEntity(String refreshToken, UserEntity user) {
        return RefreshTokenEntity.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();

    }

    public static RefreshTokenDTO toDTO(RefreshTokenEntity refreshTokenEntity) {
        return RefreshTokenDTO.builder()
                .refreshToken(refreshTokenEntity.getRefreshToken())
                .build();
    }

    public static TokenDTO toTokenDTO(String accessToken) {
        return TokenDTO.builder()
                .accessToken(accessToken)
                .build();
    }

}
