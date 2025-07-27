package com.instar.service.impl;
import com.instar.dto.DeviceDto;
import com.instar.entity.Device;
import com.instar.entity.User;
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
    public DeviceDto register(DeviceDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Device e = deviceMapper.toEntity(dto, user);
        e = deviceRepository.save(e);
        return deviceMapper.toDto(e);
    }

    @Override
    public void remove(Integer deviceId) {
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
}
