package com.instar.mapper;

import com.instar.dto.FollowDto;
import com.instar.entity.Follow;
import com.instar.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FollowMapperTest {

    FollowMapper mapper = new FollowMapper();

    @Test
    void testEntityToDto() {
        User follower = User.builder().id(100).build();
        User following = User.builder().id(200).build();
        LocalDateTime now = LocalDateTime.now();
        Follow follow = Follow.builder()
                .id(1)
                .follower(follower)
                .following(following)
                .createdAt(now)
                .build();

        FollowDto dto = mapper.toDto(follow);

        assertNotNull(dto);
        assertEquals(follow.getId(), dto.getId());
        assertEquals(follower.getId(), dto.getFollowerId());
        assertEquals(following.getId(), dto.getFollowingId());
        assertEquals(now, dto.getCreatedAt());
        System.out.println("testEntityToDto thành công");
    }

    @Test
    void testDtoToEntity() {
        FollowDto dto = FollowDto.builder()
                .id(2)
                .followerId(101)
                .followingId(201)
                .createdAt(LocalDateTime.now())
                .build();

        User follower = User.builder().id(dto.getFollowerId()).build();
        User following = User.builder().id(dto.getFollowingId()).build();

        Follow follow = mapper.toEntity(dto, follower, following);

        assertNotNull(follow);
        assertEquals(dto.getId(), follow.getId());
        assertEquals(follower, follow.getFollower());
        assertEquals(following, follow.getFollowing());
        assertEquals(dto.getCreatedAt(), follow.getCreatedAt());
        System.out.println("testDtoToEntity thành công");
    }
}
