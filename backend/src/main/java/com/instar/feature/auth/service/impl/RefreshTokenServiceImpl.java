package com.instar.feature.auth.service.impl;
import com.instar.common.exception.BusinessException;
import com.instar.common.exception.errorcode.AuthError;
import com.instar.common.util.JwtUtil;
import com.instar.feature.auth.entity.RefreshToken;
import com.instar.feature.auth.repository.RefreshTokenRepository;
import com.instar.feature.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    @Override
    public RefreshToken handleLogin(UUID userId, String token, long refreshExpiresIn) {
        LocalDateTime expiresAt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + refreshExpiresIn),
                ZoneId.systemDefault()
        );
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(token)
                .expiresAt(expiresAt)
                .revoked(false)
                .build();

        RefreshToken saved = refreshTokenRepository.save(refreshToken);
        log.info("[REFRESH] Tạo refresh token thành công cho userId={}", userId);
        return saved;
    }

    @Override
    public RefreshToken validateAndRotate(UUID userId, String token, long refreshExpiresIn) {
        RefreshToken existing = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(AuthError.INVALID_TOKEN, "Refresh token không tồn tại"));

        if (existing.getRevoked()) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Refresh token đã bị thu hồi");
        }

        if (existing.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Refresh token đã hết hạn");
        }

        existing.setRevoked(true);
        refreshTokenRepository.save(existing);

        token = jwtUtil.createRefreshToken(userId);
        return handleLogin(userId, token, refreshExpiresIn);
    }

    @Override
    public void revokeToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(rt -> {
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        });
    }
}
