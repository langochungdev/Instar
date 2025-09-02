package com.instar.feature.social.dto;

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
public class FollowDto {
    private UUID id;
    private UUID followerId;
    private UUID followingId;
    private LocalDateTime createdAt;
}
