package com.instar.mapper;

import com.instar.entity.PostMedia;
import com.instar.entity.Post;
import com.instar.dto.PostMediaDto;
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
