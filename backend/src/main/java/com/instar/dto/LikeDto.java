package com.instar.dto;
import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private LocalDateTime createdAt;
}
