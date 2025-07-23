package com.instar.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBehaviorDto {
    private Integer id;
    private Integer userId;
    private String action;
    private Integer targetId;
    private LocalDateTime createdAt;
}
