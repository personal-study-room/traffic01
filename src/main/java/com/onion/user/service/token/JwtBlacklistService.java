package com.onion.user.service.token;

import com.onion.user.entity.JwtBlacklistEntity;
import com.onion.user.mapper.JwtBlacklistMapper;
import com.onion.user.repository.JwtBlacklistRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final JwtBlacklistRepository jwtBlacklistRepository;

    @Transactional
    public void createBlacklistToken(String token, LocalDateTime expiredTime) {
        JwtBlacklistEntity jwtBlacklist = JwtBlacklistMapper.toEntity(token, expiredTime);
        jwtBlacklistRepository.save(jwtBlacklist);
    }

    @Transactional(readOnly = true)
    public boolean isTokenBlacklisted(String token) {
        Optional<JwtBlacklistEntity> blacklistTokenOptional = jwtBlacklistRepository.findByToken(token);

        return blacklistTokenOptional.isPresent();
    }

}
