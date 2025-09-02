package com.instar.feature.user.service;
import java.util.UUID;

public interface DeviceService {
    void handleLoginDevice(UUID userId, String deviceId, String deviceName, String pushToken);
    void deactivateDevice(UUID userId, String deviceId);
}
