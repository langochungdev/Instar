package com.instar.mapper;
import com.instar.entity.Device;
import com.instar.dto.DeviceDto;
import com.instar.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public DeviceDto toDto(Device e) {
        return DeviceDto.builder()
                .id(e.getId())
                .userId(e.getUserId().getId())
                .deviceToken(e.getDeviceToken())
                .deviceName(e.getDeviceName())
                .lastLogin(e.getLastLogin())
                .isActive(e.getIsActive())
                .build();
    }

    public Device toEntity(DeviceDto dto, User user) {
        return Device.builder()
                .id(dto.getId())
                .userId(user)
                .deviceToken(dto.getDeviceToken())
                .deviceName(dto.getDeviceName())
                .lastLogin(dto.getLastLogin())
                .isActive(dto.getIsActive())
                .build();
    }
}
