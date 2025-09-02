package com.instar.feature.social.mapper;

import com.instar.feature.social.dto.FollowDto;
import com.instar.feature.social.entity.Follow;
import com.instar.feature.user.entity.User;
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
