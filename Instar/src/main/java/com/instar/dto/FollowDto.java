package com.instar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FollowDto {
    private Integer id;
    private Integer followerId;
    private Integer followingId;
    private LocalDateTime createdAt;
}
