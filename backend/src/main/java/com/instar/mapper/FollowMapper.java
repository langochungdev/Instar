package com.instar.mapper;
import com.instar.entity.Follow;
import com.instar.dto.FollowDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    public FollowDto toDto(Follow e) {
        return FollowDto.builder()
                .id(e.getId())
                .followerId(e.getFollowerId().getId())
                .followingId(e.getFollowingId().getId())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public Follow toEntity(FollowDto dto, User follower, User following) {
        return Follow.builder()
                .id(dto.getId())
                .followerId(follower)
                .followingId(following)
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
