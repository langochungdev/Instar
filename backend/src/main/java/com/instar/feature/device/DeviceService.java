package com.instar.feature.device;
import java.util.List;

public interface DeviceService {
    DeviceDto register(DeviceDto deviceDto);
    void remove(Integer deviceId);
    List<DeviceDto> findByUserId(Integer userId);
    DeviceDto findByToken(String deviceToken);
}
