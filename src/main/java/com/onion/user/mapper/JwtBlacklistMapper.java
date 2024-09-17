package com.onion.user.mapper;


import com.onion.user.entity.JwtBlacklistEntity;
import java.time.LocalDateTime;

public class JwtBlacklistMapper {

    public static JwtBlacklistEntity toEntity(String token, LocalDateTime expiryTime) {
        return JwtBlacklistEntity.builder()
                .token(token)
                .expiryTime(expiryTime)
                .build();
    }
}
