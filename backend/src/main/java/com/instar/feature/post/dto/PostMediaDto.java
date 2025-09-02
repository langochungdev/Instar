package com.instar.feature.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostMediaDto {
    private UUID id;
    private UUID postId;
    private String mediaUrl;
    private String mediaType;
}
