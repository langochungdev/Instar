package com.instar.feature.social.mapper;

import com.instar.feature.post.entity.Post;
import com.instar.feature.social.dto.SavedPostDto;
import com.instar.feature.social.entity.SavedPost;
import com.instar.feature.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SavedPostMapper {
    public SavedPostDto toDto(SavedPost e) {
        return SavedPostDto.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .postId(e.getPost().getId())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public SavedPost toEntity(SavedPostDto dto, User user, Post post) {
        return SavedPost.builder()
                .id(dto.getId())
                .user(user)
                .post(post)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
