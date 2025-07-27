package com.instar.mapper;

import com.instar.dto.DeviceDto;
import com.instar.entity.Device;
import com.instar.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceMapperTest {

    DeviceMapper mapper = new DeviceMapper();

    @Test
    void testEntityToDto() {
        User user = User.builder().id(10).build();
        Device device = Device.builder()
                .id(1)
                .user(user)
                .deviceToken("token123")
                .deviceName("iPhone 15")
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .build();

        DeviceDto dto = mapper.toDto(device);

        assertNotNull(dto);
        assertEquals(device.getId(), dto.getId());
        assertEquals(user.getId(), dto.getUserId());
        assertEquals(device.getDeviceToken(), dto.getDeviceToken());
        assertEquals(device.getDeviceName(), dto.getDeviceName());
        assertEquals(device.getLastLogin(), dto.getLastLogin());
        assertEquals(device.getIsActive(), dto.getIsActive());
        System.out.println("testEntityToDto thành công");
    }

    @Test
    void testDtoToEntity() {
        DeviceDto dto = DeviceDto.builder()
                .id(2)
                .userId(20)
                .deviceToken("tokenABC")
                .deviceName("Samsung S24")
                .lastLogin(LocalDateTime.now())
                .isActive(false)
                .build();

        User user = User.builder().id(dto.getUserId()).build();

        Device entity = mapper.toEntity(dto, user);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(user, entity.getUser());
        assertEquals(dto.getDeviceToken(), entity.getDeviceToken());
        assertEquals(dto.getDeviceName(), entity.getDeviceName());
        assertEquals(dto.getLastLogin(), entity.getLastLogin());
        assertEquals(dto.getIsActive(), entity.getIsActive());
        System.out.println("testDtoToEntity thành công");
    }
}
