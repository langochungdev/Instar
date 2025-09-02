package com.instar.infrastructure.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserIdHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        Object userId = attributes.get("userId");
        if (userId != null) {
            return new StompPrincipal(userId.toString());
        }
        return super.determineUser(request, wsHandler, attributes);
    }

    static class StompPrincipal implements Principal {
        private final String name;
        public StompPrincipal(String name) { this.name = name; }
        @Override
        public String getName() { return name; }
    }
}
