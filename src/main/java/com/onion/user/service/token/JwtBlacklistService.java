package com.onion.user.service.token;

import com.onion.user.entity.JwtBlacklistEntity;
import com.onion.user.mapper.JwtBlacklistMapper;
import com.onion.user.repository.JwtBlacklistRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final JwtBlacklistRepository jwtBlacklistRepository;
    private final JwtBlacklistMapper jwtBlacklistMapper;

    public void createBlacklistToken(String token, LocalDateTime expiredTime) {
        JwtBlacklistEntity jwtBlacklist = jwtBlacklistMapper.toEntity(token, expiredTime);
        jwtBlacklistRepository.save(jwtBlacklist);
    }

    public boolean isTokenBlacklisted(String token) {
        Optional<JwtBlacklistEntity> blacklistTokenOptional = jwtBlacklistRepository.findByToken(token);

        return blacklistTokenOptional.isPresent();
    }

}
