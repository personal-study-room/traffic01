package com.onion.user.mapper;


import com.onion.user.domain.UserDetailsImpl;
import com.onion.user.domain.UserRole;
import com.onion.user.dto.user.UserDTO;
import com.onion.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(String username, String password, String email, UserRole role) {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .username(username)
                .userRole(role)
                .build();
    }

    public UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .build();
    }

    public UserDetailsImpl toUserDetails(UserEntity userEntity) {
        return UserDetailsImpl.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .userId(userEntity.getId())
                .password(userEntity.getPassword())
                .build();
    }

}
