package com.instar.mapper;

import com.instar.entity.SavedPost;
import com.instar.dto.SavedPostDto;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SavedPostMapper {
    public SavedPostDto toDto(SavedPost entity) {
        if (entity == null) return null;
        return SavedPostDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .postId(entity.getPost() != null ? entity.getPost().getId() : null)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public SavedPost toEntity(SavedPostDto dto, User user, Post post) {
        if (dto == null) return null;
        return SavedPost.builder()
                .id(dto.getId())
                .user(user)
                .post(post)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
