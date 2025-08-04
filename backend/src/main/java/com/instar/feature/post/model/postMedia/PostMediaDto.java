package com.instar.feature.post.model.postMedia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostMediaDto {
    private Integer id;
    private Integer postId;
    private String mediaUrl;
    private String mediaType;
}
