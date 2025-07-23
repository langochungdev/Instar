package com.instar.dto;
import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowDto {
    private Integer id;
    private Integer followerId;
    private Integer followingId;
    private LocalDateTime createdAt;
}
