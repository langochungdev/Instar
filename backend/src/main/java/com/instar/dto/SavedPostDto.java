package com.instar.dto;
import lombok.*;
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
