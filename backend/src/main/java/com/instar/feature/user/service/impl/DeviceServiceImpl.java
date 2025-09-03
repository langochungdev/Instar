package com.instar.feature.user.service.impl;
import com.instar.feature.user.entity.Device;
import com.instar.feature.user.entity.User;
import com.instar.feature.user.repository.DeviceRepository;
import com.instar.feature.user.repository.UserRepository;
import com.instar.feature.user.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public void handleLoginDevice(UUID userId, String deviceId, String deviceName, String pushToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Device device = deviceRepository
                .findByUserIdAndDeviceId(userId, deviceId)
                .orElse(Device.builder()
                        .user(user)
                        .deviceId(deviceId)
                        .build());

        device.setDeviceName(deviceName);
        device.setPushToken(pushToken);
        device.setLastLogin(LocalDateTime.now());
        device.setIsActive(true);

        deviceRepository.save(device);
    }

    public void deactivateDevice(UUID userId, String deviceId) {
        deviceRepository.findByUserIdAndDeviceId(userId, deviceId)
                .ifPresent(d -> {
                    d.setIsActive(false);
                    deviceRepository.save(d);
                });
    }

    @Override
    public List<Device> getMyDevices(UUID userId) {
        return deviceRepository.findByUserId(userId);
    }

    @Override
    public void revokeDevice(UUID userId, UUID deviceId) {
        deviceRepository.findById(deviceId)
                .filter(d -> d.getUser().getId().equals(userId))
                .ifPresent(d -> {
                    d.setIsActive(false);
                    deviceRepository.save(d);
                });
    }
}

