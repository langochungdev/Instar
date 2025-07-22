package com.instar.mapper;

import com.instar.entity.User;
import com.instar.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .isVerified(user.getIsVerified())
                .build();
    }

    public User toEntity(UserDto dto) {
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .fullname(dto.getFullname())
                .avatarUrl(dto.getAvatarUrl())
                .bio(dto.getBio())
                .createdAt(dto.getCreatedAt())
                .lastLogin(dto.getLastLogin())
                .isActive(dto.getIsActive())
                .isVerified(dto.getIsVerified())
                .build();
    }
}
