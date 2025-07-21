package com.instar.mapper;

import com.instar.entity.Device;
import com.instar.dto.DeviceDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public DeviceDto toDto(Device entity) {
        if (entity == null) return null;
        return DeviceDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .deviceToken(entity.getDeviceToken())
                .deviceName(entity.getDeviceName())
                .lastLogin(entity.getLastLogin())
                .isActive(entity.getIsActive())
                .build();
    }

    public Device toEntity(DeviceDto dto, User user) {
        if (dto == null) return null;
        return Device.builder()
                .id(dto.getId())
                .user(user)
                .deviceToken(dto.getDeviceToken())
                .deviceName(dto.getDeviceName())
                .lastLogin(dto.getLastLogin())
                .isActive(dto.getIsActive())
                .build();
    }
}
