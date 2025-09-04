package com.instar.feature.auth.service.impl;
import com.instar.common.exception.BusinessException;
import com.instar.common.exception.errorcode.AuthError;
import com.instar.common.service.TokenBlacklistService;
import com.instar.common.util.JwtUtil;
import com.instar.feature.auth.dto.AuthRequest;
import com.instar.feature.auth.dto.AuthResponse;
import com.instar.feature.auth.dto.RegisterRequest;
import com.instar.feature.auth.entity.RefreshToken;
import com.instar.feature.auth.service.AuthService;
import com.instar.feature.auth.service.RefreshTokenService;
import com.instar.feature.user.service.DeviceService;
import com.instar.feature.user.dto.UserDto;
import com.instar.feature.user.entity.User;
import com.instar.feature.user.mapper.UserMapper;
import com.instar.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final DeviceService deviceService;
    private final RefreshTokenService refreshTokenService;
    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(AuthError.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(AuthError.PASSWORD_NOT_MATCH);
        }

        deviceService.handleLoginDevice(
                user.getId(),
                request.getDeviceId(),
                request.getDeviceName(),
                request.getPushToken()
        );

        Set<String> roles = user.getUserRoles().stream()
                .map(ur -> ur.getRole().getName())
                .collect(Collectors.toSet());

        AuthResponse.User dto = AuthResponse.User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(roles)
                .build();

        long accessExpiresIn = jwtUtil.getAccessExpiration();
        long refreshExpiresIn = jwtUtil.getRefreshExpiration();

        String accessToken = jwtUtil.createToken(dto.getUsername(), dto.getId(), roles);
        String refreshToken = jwtUtil.createRefreshToken(dto.getId());

        refreshTokenService.handleLogin(user.getId(), refreshToken, refreshExpiresIn);

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(accessExpiresIn)
                .refreshExpiresIn(refreshExpiresIn)
                .user(dto)
                .build();
    }

    @Override
    public void logout(String refreshToken, String deviceId) {
        try {
            if (refreshToken != null && jwtUtil.validateOrThrow(refreshToken)) {
                UUID userId = jwtUtil.extractUserId(refreshToken);

                deviceService.deactivateDevice(userId, deviceId);

                refreshTokenService.revokeToken(refreshToken);
                log.info("[AUTH] Logout thành công user={}, device deactivated={}, refreshtoken={}",
                        userId, deviceId, refreshToken);
            }
        } catch (Exception e) {
            log.error("[AUTH] Lỗi khi logout: {}", e.getMessage(), e);
        }
    }

    @Override
    public UserDto register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        log.info("[AUTH] User mới đăng ký: username={} email={} id={}",
                user.getUsername(), user.getEmail(), user.getId());
        return userMapper.toDto(user);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        jwtUtil.validateRefreshOrThrow(refreshToken);
        UUID userId = jwtUtil.extractUserId(refreshToken);

        RefreshToken newRefresh = refreshTokenService.validateAndRotate(userId, refreshToken, jwtUtil.getRefreshExpiration());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(AuthError.USER_NOT_FOUND));
        Set<String> roles = user.getUserRoles().stream()
                .map(ur -> ur.getRole().getName())
                .collect(Collectors.toSet());

        AuthResponse.User dto = AuthResponse.User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(roles)
                .build();

        long accessExpiresIn = jwtUtil.getAccessExpiration();
        String newAccessToken = jwtUtil.createToken(user.getUsername(), user.getId(), roles);

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefresh.getToken())
                .expiresIn(accessExpiresIn)
                .refreshExpiresIn(jwtUtil.getRefreshExpiration())
                .user(dto)
                .build();
    }


}
