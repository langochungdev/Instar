package com.instar.service;
import com.instar.dto.DeviceDto;
import java.util.List;

public interface DeviceService {
    DeviceDto register(DeviceDto deviceDto);
    void remove(Integer deviceId);
    List<DeviceDto> findByUserId(Integer userId);
    DeviceDto findByToken(String deviceToken);
}
