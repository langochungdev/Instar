package com.instar.feature.userBehavior;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
