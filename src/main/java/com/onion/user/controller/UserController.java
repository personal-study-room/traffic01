package com.onion.user.controller;


import com.onion.common.jwt.JwtProvider;
import com.onion.user.dto.token.TokenDTO;
import com.onion.user.dto.user.UserCreateDTO;
import com.onion.user.dto.user.UserLoginDTO;
import com.onion.user.service.token.JwtBlacklistService;
import com.onion.user.service.user.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final JwtProvider jwtProvider;
    private final UserCommandService userCommandService;
    private final JwtBlacklistService jwtBlacklistService;

    @Operation
    @PostMapping("/actions/sign-up")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateDTO dto) {
        userCommandService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/actions/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO dto, HttpServletResponse response) {
        TokenDTO token = userCommandService.login(dto);

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/actions/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String headerToken,
            HttpServletResponse response) {

        String token = jwtProvider.resolveToken(headerToken);

        // 로그아웃했으면 해당 토큰은 더이상 사용하지 못하도록 블랙 리스트에 추가
        LocalDateTime expiredTime = jwtProvider.getExpiredTime(token);
        jwtBlacklistService.createBlacklistToken(token, expiredTime);

        return ResponseEntity.ok().build();
    }

    @Operation
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userCommandService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
