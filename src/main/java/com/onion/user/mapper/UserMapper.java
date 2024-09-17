package com.onion.user.mapper;


import com.onion.user.domain.UserDetailsImpl;
import com.onion.user.domain.UserRole;
import com.onion.user.dto.user.UserDTO;
import com.onion.user.entity.UserEntity;
import java.util.UUID;

public class UserMapper {

    public static UserEntity toEntityOnlyId(UUID userId) {
        return UserEntity.builder().id(userId).build();
    }

    public static UserEntity toEntity(String username, String password, String email, UserRole role) {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .username(username)
                .userRole(role)
                .build();
    }

    public static UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .build();
    }

    public static UserDetailsImpl toUserDetails(UserEntity userEntity) {
        return UserDetailsImpl.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .userId(userEntity.getId())
                .password(userEntity.getPassword())
                .userRole(userEntity.getUserRole())
                .build();
    }

}
