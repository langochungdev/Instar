package com.instar.feature.userBehavior;

import com.instar.feature.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserBehaviorMapper {
    public UserBehaviorDto toDto(UserBehavior e) {
        return UserBehaviorDto.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .action(e.getAction())
                .targetId(e.getTargetId())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public UserBehavior toEntity(UserBehaviorDto dto, User user) {
        return UserBehavior.builder()
                .id(dto.getId())
                .user(user)
                .action(dto.getAction())
                .targetId(dto.getTargetId())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
