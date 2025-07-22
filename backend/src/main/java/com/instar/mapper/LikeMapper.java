package com.instar.mapper;

import com.instar.entity.Like;
import com.instar.dto.LikeDto;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
    public LikeDto toDto(Like like) {
        if (like == null) return null;
        return LikeDto.builder()
                .id(like.getId())
                .postId(like.getPost() != null ? like.getPost().getId() : null)
                .userId(like.getUser() != null ? like.getUser().getId() : null)
                .createdAt(like.getCreatedAt())
                .build();
    }

    public Like toEntity(LikeDto dto, Post post, User user) {
        if (dto == null) return null;
        return Like.builder()
                .id(dto.getId())
                .post(post)
                .user(user)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
