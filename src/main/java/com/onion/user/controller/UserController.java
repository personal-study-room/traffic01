package com.onion.user.controller;


import com.onion.user.dto.token.TokenDTO;
import com.onion.user.dto.user.UserCreateDTO;
import com.onion.user.dto.user.UserLoginDTO;
import com.onion.user.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserCommandService userCommandService;

    @Operation
    @PostMapping("/actions/sign-up")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateDTO dto) {
        userCommandService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/actions/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO dto) {
        return ResponseEntity.ok().body(userCommandService.login(dto));
    }

    @Operation
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userCommandService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
