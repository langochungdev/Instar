package com.instar.feature.auth;
import com.instar.common.exception.ErrorResponder;
import com.instar.common.service.TokenBlacklistService;
import com.instar.common.util.CurrentUserUtil;
import com.instar.common.util.JwtUtil;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import com.instar.feature.user.UserMapper;
import com.instar.feature.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;
    private final CurrentUserUtil currentUserUtil;

    @Override
    public ResponseEntity<?> login(AuthRequest request, HttpServletResponse response) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            ErrorResponder.sendError(response, "sai tai khoan");
            return ResponseEntity.badRequest().build();
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            ErrorResponder.sendError(response, "sai mat khau");
            return ResponseEntity.badRequest().build();
        }

        String token = jwtUtil.createToken(user.getUsername(), String.valueOf(user.getId()), user.getRole());
        long expiresIn = jwtUtil.getExpiration();

        AuthResponse.User dto = AuthResponse.User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .expiresIn(expiresIn)
                .user(dto)
                .build();

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(expiresIn / 1000)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResponse);
    }

    @Override
    public UserDto register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public ResponseEntity<?> logout(String token, HttpServletResponse response) {
        if (token != null && jwtUtil.validateToken(token)) {
            long expiration = jwtUtil.getExpirationFromToken(token) - System.currentTimeMillis();
            tokenBlacklistService.blacklistToken(token, expiration);
        }
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/me")
    public ResponseEntity<?> checkStatus() {
        User user = currentUserUtil.getUser();
        AuthResponse.User dto = AuthResponse.User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
        return ResponseEntity.ok(dto);
    }
}
