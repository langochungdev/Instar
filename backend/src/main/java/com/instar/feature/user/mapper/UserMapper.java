package com.instar.feature.user.mapper;

import com.instar.feature.user.dto.UserDto;
import com.instar.feature.user.entity.Role;
import com.instar.feature.user.entity.User;
import com.instar.feature.user.entity.UserRole;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .isVerified(user.getIsVerified())
                .roles(user.getUserRoles()
                        .stream()
                        .map(ur -> ur.getRole().getName())
                        .collect(Collectors.toSet()))
                .build();
    }

    public User toEntity(UserDto dto) {
        User user = User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .avatarUrl(dto.getAvatarUrl())
                .bio(dto.getBio())
                .createdAt(dto.getCreatedAt())
                .lastLogin(dto.getLastLogin())
                .isActive(dto.getIsActive())
                .isVerified(dto.getIsVerified())
                .build();

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<UserRole> userRoles = dto.getRoles().stream()
                    .map(roleName -> UserRole.builder()
                            .user(user)
                            .role(Role.builder().name(roleName).build())
                            .build())
                    .collect(Collectors.toSet());
            user.setUserRoles(userRoles);
        }

        return user;
    }

    public void updateEntityFromDto(UserDto dto, User user) {
        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getAvatarUrl() != null) {
            user.setAvatarUrl(dto.getAvatarUrl());
        }
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }
        if (dto.getIsActive() != null) {
            user.setIsActive(dto.getIsActive());
        }
        if (dto.getIsVerified() != null) {
            user.setIsVerified(dto.getIsVerified());
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<UserRole> userRoles = dto.getRoles().stream()
                    .map(roleName -> UserRole.builder()
                            .user(user)
                            .role(Role.builder().name(roleName).build())
                            .build())
                    .collect(Collectors.toSet());
            user.setUserRoles(userRoles);
        }
    }



}
