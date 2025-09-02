package com.instar.feature.notification.dto;

import com.instar.feature.notification.entity.NotificationTargetType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private UUID id;
    private UUID userId;
    private String type;
    private String message;
    private String link;
    private UUID targetId;                       // [SỬA] thêm targetId
    private NotificationTargetType targetType;   // [SỬA] thêm targetType
    private Boolean isRead;
    private LocalDateTime createdAt;
}
