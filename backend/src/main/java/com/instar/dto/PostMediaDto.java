package com.instar.dto;
import lombok.*;

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
