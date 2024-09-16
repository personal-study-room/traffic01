package com.onion.user.entity;


import static jakarta.persistence.FetchType.LAZY;

import com.onion.common.entity.BaseEntity;
import com.onion.user.domain.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Table(indexes = {
        @Index(name = "idx_email", columnList = "email")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDateTime lastLogin;

    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private RefreshTokenEntity refreshToken;

    public void setRefreshToken(RefreshTokenEntity savedRefreshToken) {
        this.refreshToken = savedRefreshToken;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
