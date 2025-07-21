package com.instar.mapper;

import com.instar.entity.Comment;
import com.instar.dto.CommentDto;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto toDto(Comment comment) {
        if (comment == null) return null;
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost() != null ? comment.getPost().getId() : null)
                .userId(comment.getUser() != null ? comment.getUser().getId() : null)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .build();
    }

    public Comment toEntity(CommentDto dto, Post post, User user, Comment parent) {
        if (dto == null) return null;
        return Comment.builder()
                .id(dto.getId())
                .post(post)
                .user(user)
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .parent(parent)
                .build();
    }
}
