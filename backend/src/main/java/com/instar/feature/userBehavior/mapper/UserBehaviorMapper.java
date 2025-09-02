package com.instar.feature.userBehavior.mapper;

import com.instar.feature.user.entity.User;
import com.instar.feature.userBehavior.dto.UserBehaviorDto;
import com.instar.feature.userBehavior.entity.UserBehavior;
import org.springframework.stereotype.Component;

@Component
public class UserBehaviorMapper {

    public UserBehaviorDto toDto(UserBehavior e) {
        return UserBehaviorDto.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .action(e.getAction())
                .targetId(e.getTargetId())
                .targetType(e.getTargetType())   // [SỬA] ánh xạ targetType
                .createdAt(e.getCreatedAt())
                .build();
    }

    public UserBehavior toEntity(UserBehaviorDto dto, User user) {
        return UserBehavior.builder()
                .id(dto.getId())
                .user(user)
                .action(dto.getAction())
                .targetId(dto.getTargetId())
                .targetType(dto.getTargetType()) // [SỬA] ánh xạ targetType
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
