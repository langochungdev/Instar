package com.instar.common.util;
import com.instar.common.exception.BusinessException;
import com.instar.common.exception.errorcode.AuthError;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;
    private Key key;
    private final long EXPIRATION = 3600000; //1h
    private final long REFRESH_EXPIRATION = 2592000000L; // 30 ngày
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String createToken(String username, UUID userId, Set<String> roles) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuer("instar")
                .claim("username", username)
                .claim("roles", roles) // nhiều role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuer("instar")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateOrThrow(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Token đã hết hạn");
        } catch (UnsupportedJwtException ex) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Token không được hỗ trợ");
        } catch (MalformedJwtException ex) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Token sai định dạng");
        } catch (SignatureException ex) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Chữ ký token không hợp lệ");
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(AuthError.MISSING_TOKEN);
        }
    }

    public boolean validateRefreshOrThrow(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Refresh token đã hết hạn");
        } catch (Exception ex) {
            throw new BusinessException(AuthError.INVALID_TOKEN, "Refresh token không hợp lệ");
        }
    }

    public long getExpirationFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .getTime();
    }

    public long getAccessExpiration() {
        return EXPIRATION;
    }

    public long getRefreshExpiration() {
        return REFRESH_EXPIRATION;
    }
    public UUID extractUserId(String token) {
        String id = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return UUID.fromString(id);
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public String getTokenBearer(HttpServletRequest request) {
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
