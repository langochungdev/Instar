package com.instar.feature.auth.service;
import com.instar.feature.auth.dto.AuthRequest;
import com.instar.feature.auth.dto.AuthResponse;
import com.instar.feature.auth.dto.RegisterRequest;
import com.instar.feature.auth.entity.RefreshToken;
import com.instar.feature.user.dto.UserDto;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken handleLogin(UUID userId, String token, long refreshExpiresIn);
    RefreshToken validateAndRotate(UUID userId, String token, long refreshExpiresIn);
    void revokeToken(String token);
}
