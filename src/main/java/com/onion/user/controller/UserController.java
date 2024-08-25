package com.onion.user.controller;


import com.onion.user.dto.UserCreateDTO;
import com.onion.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @Operation
  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody UserCreateDTO dto) {
    userService.createUser(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
