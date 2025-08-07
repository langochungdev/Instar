package com.instar.common.util;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;
    private Key key;
//    private final long REFRESH_EXPIRATION = 604800000; // 7 ngày
    private final long EXPIRATION = 604800000; // 1 giờ = 3600000
    //khởi tạo bổ sung sau khi spring khởi tạo bean và inject denpen
    // đảm bảo secret đã được inject tránh null
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET.getBytes()); //HMAC phương pháp xác thực dữ liệu
    }

    public String createToken(String username, String userId, String role) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuer("instar")
                .claim("username", username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


//    public String createRefreshToken(String username, String userId, String role) {
//        return Jwts.builder()
//                .setSubject(userId)
//                .claim("username", username)
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public long getExpiration() {
        return EXPIRATION;
    }

    // mặc định lấy key sub trong payload
    public String extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

}
