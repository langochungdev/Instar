package com.instar.feature.userBehavior.dto;
import com.instar.feature.userBehavior.entity.BehaviorTargetType;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBehaviorDto {
    private UUID id;
    private UUID userId;
    private String action;
    private UUID targetId;
    private BehaviorTargetType targetType; // [SỬA] thêm targetType
    private LocalDateTime createdAt;
}
