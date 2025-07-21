package com.instar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
    private Integer parentId;
}
