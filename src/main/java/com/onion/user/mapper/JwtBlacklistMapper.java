package com.onion.user.mapper;


import com.onion.user.entity.JwtBlacklistEntity;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class JwtBlacklistMapper {

    public JwtBlacklistEntity toEntity(String token, LocalDateTime expiryTime) {
        return JwtBlacklistEntity.builder()
                .token(token)
                .expiryTime(expiryTime)
                .build();
    }
}
