package com.instar.mapper;
import com.instar.entity.SavedPost;
import com.instar.dto.SavedPostDto;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SavedPostMapper {
    public SavedPostDto toDto(SavedPost e) {
        return SavedPostDto.builder()
                .id(e.getId())
                .userId(e.getUserId().getId())
                .postId(e.getPostId().getId())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public SavedPost toEntity(SavedPostDto dto, User user, Post post) {
        return SavedPost.builder()
                .id(dto.getId())
                .userId(user)
                .postId(post)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
