package com.instar.service.impl;
import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.UserDto;
import com.instar.entity.User;
import com.instar.mapper.UserMapper;
import com.instar.repository.UserRepository;
import com.instar.service.UserService;
import com.instar.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    public UserDto create(UserDto dto) {
        User e = userMapper.toEntity(dto);
        e = userRepository.save(e);
        return userMapper.toDto(e);
    }

    @Override
    public UserDto update(Integer id, UserDto dto) {
        User e = userRepository.findById(id).orElse(null);
        if (e == null) return null;
        e.setFullname(dto.getFullname());
        e.setAvatarUrl(dto.getAvatarUrl());
        e.setBio(dto.getBio());
        e.setIsActive(dto.getIsActive());
        e.setIsVerified(dto.getIsVerified());
        e.setLastLogin(dto.getLastLogin());
        e = userRepository.save(e);
        return userMapper.toDto(e);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findByUsername(String username) {
        return userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    public UserDto register(UserDto dto) {
        User e = userMapper.toEntity(dto);
        e = userRepository.save(e);
        return userMapper.toDto(e);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) return null;
        String token = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        long expiresIn = jwtUtil.getExpiration();
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }

    @Override
    public void changePassword(Integer id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElse(null);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) return;
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void verifyAccount(Integer id, String code) {
        User user = userRepository.findById(id).orElse(null);
        // Ví dụ tạm thời bỏ qua check, chỉ xác thực
        user.setIsVerified(true);
        userRepository.save(user);
    }
}
