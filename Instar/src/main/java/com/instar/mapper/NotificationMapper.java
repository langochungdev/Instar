package com.instar.mapper;

import com.instar.entity.Notification;
import com.instar.dto.NotificationDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDto toDto(Notification entity) {
        if (entity == null) return null;
        return NotificationDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .type(entity.getType())
                .message(entity.getMessage())
                .link(entity.getLink())
                .isRead(entity.getIsRead())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public Notification toEntity(NotificationDto dto, User user) {
        if (dto == null) return null;
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
