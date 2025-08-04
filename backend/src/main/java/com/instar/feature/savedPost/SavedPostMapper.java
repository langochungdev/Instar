package com.instar.feature.savedPost;

import com.instar.feature.post.model.post.Post;
import com.instar.feature.user.User;
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
