package com.instar.service;
import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.UserDto;
import com.instar.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserDto register(User user);
    AuthResponse refreshToken(HttpServletRequest refreshToken);
}
