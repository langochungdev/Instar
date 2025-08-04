package com.instar.feature.device;

import com.instar.common.exception.NoPermissionException;
import com.instar.common.util.CurrentUserUtil;
import com.instar.feature.user.User;
import com.instar.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!dto.getUserId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Device e = deviceMapper.toEntity(dto, user);
        e = deviceRepository.save(e);
        return deviceMapper.toDto(e);
    }

    @Override
    public void remove(Integer deviceId) {
        Device e = deviceRepository.findById(deviceId).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (e == null || (!e.getUser().getId().equals(currentUserId) && !admin)) {
            throw new NoPermissionException();
        }
        deviceRepository.deleteById(deviceId);
    }

    @Override
    public List<DeviceDto> findByUserId(Integer userId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return deviceRepository.findAll().stream()
                .filter(d -> d.getUser().getId().equals(userId))
                .map(deviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDto findByToken(String deviceToken) {
        Device d = deviceRepository.findAll().stream()
                .filter(dev -> dev.getDeviceToken().equals(deviceToken))
                .findFirst()
                .orElse(null);
        if (d == null) return null;

        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!d.getUser().getId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return deviceMapper.toDto(d);
    }
}
