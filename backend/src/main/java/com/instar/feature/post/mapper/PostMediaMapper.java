package com.instar.feature.post.mapper;

import com.instar.feature.post.dto.PostMediaDto;
import com.instar.feature.post.entity.Post;
import com.instar.feature.post.entity.PostMedia;
import org.springframework.stereotype.Component;

@Component
public class PostMediaMapper {
    public PostMediaDto toDto(PostMedia e) {
        return PostMediaDto.builder()
                .id(e.getId())
                .postId(e.getPost().getId())
                .mediaUrl(e.getMediaUrl())
                .mediaType(e.getMediaType())
                .build();
    }

    public PostMedia toEntity(PostMediaDto dto, Post post) {
        return PostMedia.builder()
                .id(dto.getId())
                .post(post)
                .mediaUrl(dto.getMediaUrl())
                .mediaType(dto.getMediaType())
                .build();
    }
}
