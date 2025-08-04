package com.instar.feature.notification;

import com.instar.feature.user.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDto toDto(Notification e) {
        return NotificationDto.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .type(e.getType())
                .message(e.getMessage())
                .link(e.getLink())
                .isRead(e.getIsRead())
                .createdAt(e.getCreatedAt())
                .build();
    }

    public Notification toEntity(NotificationDto dto, User user) {
        return Notification.builder()
                .id(dto.getId())
                .user(user)
                .type(dto.getType())
                .message(dto.getMessage())
                .link(dto.getLink())
                .isRead(dto.getIsRead())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
