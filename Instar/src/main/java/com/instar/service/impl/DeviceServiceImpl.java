package com.instar.service.impl;

import com.instar.dto.DeviceDto;
import com.instar.entity.Device;
import com.instar.entity.User;
import com.instar.exception.CustomException;
import com.instar.mapper.DeviceMapper;
import com.instar.repository.DeviceRepository;
import com.instar.repository.UserRepository;
import com.instar.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final DeviceMapper deviceMapper;

    @Override
    public DeviceDto registerDevice(DeviceDto deviceDto) {
        User user = userRepository.findById(deviceDto.getUserId()).orElse(null);
        Device device = deviceMapper.toEntity(deviceDto, user);
        device = deviceRepository.save(device);
        return deviceMapper.toDto(device);
    }

    @Override
    public void removeDevice(Integer deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    @Override
    public List<DeviceDto> findByUserId(Integer userId) {
        return deviceRepository.findAll().stream()
                .filter(d -> d.getUser().getId().equals(userId))
                .map(deviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDto findByToken(String deviceToken) {
        return deviceRepository.findAll().stream()
                .filter(d -> d.getDeviceToken().equals(deviceToken))
                .findFirst()
                .map(deviceMapper::toDto)
                .orElse(null);
    }

    @Override
    public DeviceDto register(DeviceDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        Device device = Device.builder()
                .user(user)
                .deviceToken(dto.getDeviceToken())
                .deviceName(dto.getDeviceName())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .build();

        device = deviceRepository.save(device);
        return deviceMapper.toDto(device);
    }

    @Override
    public void remove(Integer deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new CustomException("Device không tồn tại"));
        deviceRepository.delete(device);
    }

}
