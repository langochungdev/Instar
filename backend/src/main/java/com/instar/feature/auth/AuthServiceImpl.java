package com.instar.feature.auth;
import com.instar.common.util.JwtUtil;
import com.instar.feature.user.User;
import com.instar.feature.user.UserDto;
import com.instar.feature.user.UserMapper;
import com.instar.feature.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) throw new RuntimeException("User không tồn tại!");
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new RuntimeException("Sai mật khẩu!");

        String token = jwtUtil.createToken(user.getUsername(), String.valueOf(user.getId()), user.getRole());
        long expiresIn = jwtUtil.getExpiration();

        UserAuthDto dto = UserAuthDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(token)
                .expiresIn(expiresIn)
                .user(dto)
                .build();

        // Tạo cookie HTTP-only
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)     // dev HTTP
                .sameSite("Lax")   // cùng-site → cookie sẽ được gửi
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

//    @Override
//    public AuthResponse refreshToken(HttpServletRequest request) {
//        String refreshToken = null;
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("refreshToken".equals(cookie.getName())) {
//                    refreshToken = cookie.getValue();
//                    break;
//                }
//            }
//        }
//        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
//            throw new RuntimeException("Refresh token không hợp lệ!");
//        }
//        String userId = jwtUtil.extractUserId(refreshToken);
//        User user = userRepository.findById(Integer.valueOf(userId)).orElse(null);
//        if (user == null) {
//            throw new RuntimeException("User không tồn tại!");
//        }
//        String newAccessToken = jwtUtil.createToken(user.getUsername(), String.valueOf(user.getId()), user.getRole());
//        String newRefreshToken = jwtUtil.createRefreshToken(user.getUsername(), String.valueOf(user.getId()), user.getRole());
//        long expiresIn = jwtUtil.getExpiration();
//        return AuthResponse.builder()
//                .accessToken(newAccessToken)
//                .refreshToken(newRefreshToken)
//                .expiresIn(expiresIn)
//                .build();
//    }

}
