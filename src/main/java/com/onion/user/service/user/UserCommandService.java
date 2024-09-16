package com.onion.user.service.user;


import com.onion.user.dto.token.TokenDTO;
import com.onion.user.dto.user.UserCreateDTO;
import com.onion.user.dto.user.UserLoginDTO;
import com.onion.user.entity.UserEntity;
import com.onion.user.mapper.UserMapper;
import com.onion.user.repository.UserRepository;
import com.onion.user.service.token.TokenCommandService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenCommandService tokenCommandService;


    public void createUser(UserCreateDTO dto) {
        Optional<UserEntity> findUser = userRepository.findByUsername(dto.getUsername());

        if (findUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        UserEntity newUser = userMapper.toEntity(dto.getUsername(), encodedPassword, dto.getEmail(), dto.getRole());
        userRepository.save(newUser);
    }


    public void deleteUser(UUID userId) {
        UserEntity findUser = userRepository.findByIdOrThrow(userId);
        userRepository.delete(findUser);
    }


    public TokenDTO login(UserLoginDTO dto) {
        UserEntity loginUser = userRepository.findByEmailOrThrow(dto.getEmail());

        if (!passwordEncoder.matches(dto.getPassword(), loginUser.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        TokenDTO tokenDTO = tokenCommandService.createToken(loginUser);

        loginUser.setLastLogin(LocalDateTime.now());

        return tokenDTO;
    }
}
