package com.instar.feature.auth.service;
import com.instar.feature.auth.dto.AuthRequest;
import com.instar.feature.auth.dto.AuthResponse;
import com.instar.feature.auth.dto.RegisterRequest;
import com.instar.feature.user.dto.UserDto;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    void logout(String token, String fingerprint);
    UserDto register(RegisterRequest request);
    AuthResponse refresh(String refreshToken);
}
