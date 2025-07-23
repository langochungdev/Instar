package com.instar.mapper;

import com.instar.dto.LikeDto;
import com.instar.entity.Like;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LikeMapperTest {

    LikeMapper mapper = new LikeMapper();

    @Test
    void testEntityToDto() {
        Post post = Post.builder().id(123).build();
        User user = User.builder().id(321).build();
        LocalDateTime now = LocalDateTime.now();
        Like like = Like.builder()
                .id(1)
                .postId(post)
                .userId(user)
                .createdAt(now)
                .build();

        LikeDto dto = mapper.toDto(like);

        assertNotNull(dto);
        assertEquals(like.getId(), dto.getId());
        assertEquals(post.getId(), dto.getPostId());
        assertEquals(user.getId(), dto.getUserId());
        assertEquals(now, dto.getCreatedAt());
        System.out.println("testEntityToDto thành công");
    }

    @Test
    void testDtoToEntity() {
        LikeDto dto = LikeDto.builder()
                .id(2)
                .postId(124)
                .userId(322)
                .createdAt(LocalDateTime.now())
                .build();

        Post post = Post.builder().id(dto.getPostId()).build();
        User user = User.builder().id(dto.getUserId()).build();

        Like like = mapper.toEntity(dto, post, user);

        assertNotNull(like);
        assertEquals(dto.getId(), like.getId());
        assertEquals(post, like.getPostId());
        assertEquals(user, like.getUserId());
        assertEquals(dto.getCreatedAt(), like.getCreatedAt());
        System.out.println("testDtoToEntity thành công");
    }
}
