package com.instar.feature.savedPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedPostDto {
    private Integer id;
    private Integer userId;
    private Integer postId;
    private LocalDateTime createdAt;
}
