package com.instar.feature.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowDto {
    private Integer id;
    private Integer followerId;
    private Integer followingId;
    private LocalDateTime createdAt;
}
