package com.onion.user.dto;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO {
  @Parameter
  private String username;
  @Parameter
  private String password;
  @Parameter
  private String email;
}
