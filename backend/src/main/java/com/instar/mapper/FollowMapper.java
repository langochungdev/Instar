package com.instar.mapper;

import com.instar.entity.Follow;
import com.instar.dto.FollowDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    public FollowDto toDto(Follow follow) {
        if (follow == null) return null;
        return FollowDto.builder()
                .id(follow.getId())
                .followerId(follow.getFollower() != null ? follow.getFollower().getId() : null)
                .followingId(follow.getFollowing() != null ? follow.getFollowing().getId() : null)
                .createdAt(follow.getCreatedAt())
                .build();
    }

    public Follow toEntity(FollowDto dto, User follower, User following) {
        if (dto == null) return null;
        return Follow.builder()
                .id(dto.getId())
                .follower(follower)
                .following(following)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
