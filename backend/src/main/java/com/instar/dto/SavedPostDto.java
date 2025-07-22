package com.instar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SavedPostDto {
    private Integer id;
    private Integer userId;
    private Integer postId;
    private LocalDateTime createdAt;
}
