package com.instar.feature.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_roles", schema = "dbo", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
