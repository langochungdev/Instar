package com.instar.feature.post.mapper;

import com.instar.feature.post.dto.PostDto;
import com.instar.feature.post.entity.Post;
import com.instar.feature.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto toDto(Post e) {
        return PostDto.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .content(e.getContent())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .isDeleted(e.getIsDeleted())
                .build();
    }

    public Post toEntity(PostDto dto, User user) {
        return Post.builder()
                .id(dto.getId())
                .user(user)
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .isDeleted(dto.getIsDeleted())
                .build();
    }
}
