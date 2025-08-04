package com.instar.feature.follow;

import com.instar.feature.user.User;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    public FollowDto toDto(Follow e) {
        return FollowDto.builder()
                .id(e.getId())
                .followerId(e.getFollower().getId())
                .followingId(e.getFollowing().getId())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public Follow toEntity(FollowDto dto, User follower, User following) {
        return Follow.builder()
                .id(dto.getId())
                .follower(follower)
                .following(following)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
