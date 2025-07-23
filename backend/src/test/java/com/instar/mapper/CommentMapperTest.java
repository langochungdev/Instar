package com.instar.mapper;

import com.instar.dto.CommentDto;
import com.instar.entity.Comment;
import com.instar.entity.Post;
import com.instar.entity.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CommentMapperTest {

    CommentMapper mapper = new CommentMapper();

    @Test
    void testEntityToDto() {
        Post post = Post.builder().id(10).build();
        User user = User.builder().id(20).build();
        Comment parent = Comment.builder().id(30).build();
        LocalDateTime now = LocalDateTime.now();
        Comment comment = Comment.builder()
                .id(1)
                .postId(post)
                .userId(user)
                .content("Nội dung bình luận")
                .createdAt(now)
                .parentId(parent)
                .build();

        CommentDto dto = mapper.toDto(comment);

        assertNotNull(dto);
        assertEquals(comment.getId(), dto.getId());
        assertEquals(post.getId(), dto.getPostId());
        assertEquals(user.getId(), dto.getUserId());
        assertEquals(parent.getId(), dto.getParentId());
        assertEquals(comment.getContent(), dto.getContent());
        assertEquals(now, dto.getCreatedAt());
        System.out.println("testEntityToDto thành công");
    }

    @Test
    void testDtoToEntity() {
        CommentDto dto = CommentDto.builder()
                .id(2)
                .postId(11)
                .userId(21)
                .content("Hello comment")
                .createdAt(LocalDateTime.now())
                .parentId(31)
                .build();

        Post post = Post.builder().id(dto.getPostId()).build();
        User user = User.builder().id(dto.getUserId()).build();
        Comment parent = Comment.builder().id(dto.getParentId()).build();

        Comment entity = mapper.toEntity(dto, post, user, parent);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(post, entity.getPostId());
        assertEquals(user, entity.getUserId());
        assertEquals(parent, entity.getParentId());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
        System.out.println("testDtoToEntity thành công");
    }
}
