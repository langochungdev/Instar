package com.instar.mapper;

import com.instar.entity.Post;
import com.instar.dto.PostDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto toDto(Post post) {
        if (post == null) return null;
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUser() != null ? post.getUser().getId() : null)
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .videoUrl(post.getVideoUrl())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .isDeleted(post.getIsDeleted())
                .build();
    }

    public Post toEntity(PostDto dto, User user) {
        if (dto == null) return null;
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
