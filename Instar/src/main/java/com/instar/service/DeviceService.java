package com.instar.service;

import com.instar.dto.DeviceDto;
import java.util.List;

public interface DeviceService {
    DeviceDto registerDevice(DeviceDto deviceDto);
    void removeDevice(Integer deviceId);
    List<DeviceDto> findByUserId(Integer userId);
    DeviceDto findByToken(String deviceToken);
    DeviceDto register(DeviceDto dto);
    void remove(Integer deviceId);
}
