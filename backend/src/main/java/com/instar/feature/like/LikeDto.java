package com.instar.feature.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private LocalDateTime createdAt;
}
