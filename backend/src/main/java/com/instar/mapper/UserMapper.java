package com.instar.mapper;
import com.instar.entity.User;
import com.instar.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User e) {
        return UserDto.builder()
                .id(e.getId())
                .username(e.getUsername())
                .email(e.getEmail())
                .fullname(e.getFullname())
                .avatarUrl(e.getAvatarUrl())
                .bio(e.getBio())
                .createdAt(e.getCreatedAt())
                .lastLogin(e.getLastLogin())
                .isActive(e.getIsActive())
                .isVerified(e.getIsVerified())
                .build();
    }

    public User toEntity(UserDto dto) {
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
