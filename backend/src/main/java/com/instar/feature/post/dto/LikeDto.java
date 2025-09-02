package com.instar.feature.post.dto;

import com.instar.feature.post.entity.LikeTargetType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {
    private UUID id;
    private UUID userId;
    private UUID targetId;
    private LikeTargetType targetType;
    private LocalDateTime createdAt;
}
