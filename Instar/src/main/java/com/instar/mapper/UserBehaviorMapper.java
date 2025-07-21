package com.instar.mapper;

import com.instar.entity.UserBehavior;
import com.instar.dto.UserBehaviorDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserBehaviorMapper {
    public UserBehaviorDto toDto(UserBehavior entity) {
        if (entity == null) return null;
        return UserBehaviorDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .action(entity.getAction())
                .targetId(entity.getTargetId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public UserBehavior toEntity(UserBehaviorDto dto, User user) {
        if (dto == null) return null;
        return UserBehavior.builder()
                .id(dto.getId())
                .user(user)
                .action(dto.getAction())
                .targetId(dto.getTargetId())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
