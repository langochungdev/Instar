package com.instar.mapper;

import com.instar.entity.Post;
import com.instar.dto.PostDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto toDto(Post e) {
        return PostDto.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .content(e.getContent())
                .imageUrl(e.getImageUrl())
                .videoUrl(e.getVideoUrl())
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
                .imageUrl(dto.getImageUrl())
                .videoUrl(dto.getVideoUrl())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .isDeleted(dto.getIsDeleted())
                .build();
    }
}
