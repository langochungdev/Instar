package com.instar.feature.user.service.impl;
import com.instar.common.exception.BusinessException;
import com.instar.common.exception.errorcode.UserError;
import com.instar.feature.user.dto.UserDto;
import com.instar.feature.user.entity.User;
import com.instar.feature.user.mapper.UserMapper;
import com.instar.feature.user.repository.UserRepository;
import com.instar.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto checkStatus(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(UserError.USER_NOT_FOUND));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateProfile(UUID userId, UserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(UserError.USER_NOT_FOUND));
        userMapper.updateEntityFromDto(dto, user);
        userRepository.save(user);
        log.info("[USER] User Cập nhật: username={} email={} id={}",
                user.getUsername(), user.getEmail(), user.getId());
        return userMapper.toDto(user);
    }

}
