package com.instar.feature.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private UUID id;
    private UUID postId;
    private UUID userId;
    private String content;
    private LocalDateTime createdAt;
    private UUID parentId;
}
