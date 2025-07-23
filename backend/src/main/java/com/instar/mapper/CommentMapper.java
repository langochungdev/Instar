package com.instar.mapper;
import com.instar.entity.Comment;
import com.instar.dto.CommentDto;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment e) {
        return CommentDto.builder()
                .id(e.getId())
                .postId(e.getPostId().getId())
                .userId(e.getUserId().getId())
                .content(e.getContent())
                .createdAt(e.getCreatedAt())
                .parentId(e.getParentId() != null ? e.getParentId().getId() : null)
                .build();
    }

    public Comment toEntity(CommentDto dto, Post post, User user, Comment parent) {
        return Comment.builder()
                .id(dto.getId())
                .postId(post)
                .userId(user)
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .parentId(parent)
                .build();
    }
}
