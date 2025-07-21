package com.instar.service.impl;

import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.UserDto;
import com.instar.entity.User;
import com.instar.exception.CustomException;
import com.instar.mapper.UserMapper;
import com.instar.repository.UserRepository;
import com.instar.service.UserService;
import com.instar.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDto create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto update(Integer id, UserDto userDto) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        user.setFullname(userDto.getFullname());
        user.setAvatarUrl(userDto.getAvatarUrl());
        user.setBio(userDto.getBio());
        user.setIsActive(userDto.getIsActive());
        user.setIsVerified(userDto.getIsVerified());
        user.setLastLogin(userDto.getLastLogin());
        user = userRepository.save(user);
        return userMapper.toDto(user);
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
        User user = userMapper.toEntity(dto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException("Sai tài khoản hoặc mật khẩu"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Sai tài khoản hoặc mật khẩu");
        }
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        // Nếu xác thực lại mật khẩu cũ
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new CustomException("Mật khẩu cũ không đúng");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    @Override
    public void verifyAccount(Integer id, String code) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        // TODO: kiểm tra code có đúng không (tùy bạn tổ chức lưu mã verify ở đâu)
        // Ví dụ tạm thời bỏ qua check, chỉ xác thực
        user.setIsVerified(true);
        userRepository.save(user);
    }


}
