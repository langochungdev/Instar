package com.instar.mapper;
import com.instar.entity.Like;
import com.instar.dto.LikeDto;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
    public LikeDto toDto(Like e) {
        return LikeDto.builder()
                .id(e.getId())
                .postId(e.getPost().getId())
                .userId(e.getUser().getId())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public Like toEntity(LikeDto dto, Post post, User user) {
        return Like.builder()
                .id(dto.getId())
                .post(post)
                .user(user)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
