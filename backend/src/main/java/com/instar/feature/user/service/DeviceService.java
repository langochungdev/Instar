package com.instar.feature.user.service;
import com.instar.feature.user.entity.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    void handleLoginDevice(UUID userId, String deviceId, String deviceName, String pushToken);
    void deactivateDevice(UUID userId, String deviceId);
    List<Device> getMyDevices(UUID userId);
    void revokeDevice(UUID userId, UUID deviceId);
}
