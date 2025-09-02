package com.instar.feature.post.mapper;

import com.instar.feature.post.dto.LikeDto;
import com.instar.feature.post.entity.Like;
import com.instar.feature.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeDto toDto(Like e) {
        return LikeDto.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .targetId(e.getTargetId())
                .targetType(e.getTargetType())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public Like toEntity(LikeDto dto, User user) {
        return Like.builder()
                .id(dto.getId())
                .user(user)
                .targetId(dto.getTargetId())
                .targetType(dto.getTargetType())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
