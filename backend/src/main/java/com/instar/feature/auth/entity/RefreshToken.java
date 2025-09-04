package com.instar.feature.auth.entity;

import com.instar.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false, columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(nullable = false, columnDefinition = "uniqueidentifier")
    private UUID userId;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @Column(nullable = false, updatable = false, columnDefinition = "datetime2 default getdate()")
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "datetime2")
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean revoked = false;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
