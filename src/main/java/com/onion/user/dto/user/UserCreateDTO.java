package com.onion.user.dto.user;


import com.onion.user.domain.UserRole;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String username;
    @Parameter
    @NotNull
    private String password;
    @Parameter
    @NotNull
    private String email;
    @NotNull
    private UserRole role;
}
