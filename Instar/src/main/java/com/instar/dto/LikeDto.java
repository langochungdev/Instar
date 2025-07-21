package com.instar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LikeDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private LocalDateTime createdAt;
}
