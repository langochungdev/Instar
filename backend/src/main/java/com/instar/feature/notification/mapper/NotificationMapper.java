package com.instar.feature.notification.mapper;

import com.instar.feature.notification.dto.NotificationDto;
import com.instar.feature.notification.entity.Notification;
import com.instar.feature.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "userId", source = "user.id")
    NotificationDto toDto(Notification e);

    @Mapping(target = "user", source = "userId")
    Notification toEntity(NotificationDto dto);

    default User map(UUID userId) {
        if (userId == null) return null;
        User u = new User();
        u.setId(userId);
        return u;
    }
}
