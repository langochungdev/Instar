package com.instar.infrastructure.interceptor;

import com.instar.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        List<String> cookies = request.getHeaders().get("cookie");
        if (cookies != null) {
            for (String c : cookies) {
                for (String part : c.split(";")) {
                    String[] kv = part.trim().split("=");
                    if (kv.length == 2 && kv[0].equals("token")) {
                        String token = kv[1];
                        if (jwtUtil.validateOrThrow(token)) {
                            UUID userId = jwtUtil.extractUserId(token);
                            attributes.put("userId", userId);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
