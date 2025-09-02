package com.instar.infrastructure.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long start = System.nanoTime();
        request.setAttribute(START_TIME, start);

        String traceId = getOrCreateTraceId(request);
        MDC.put("traceId", traceId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        try {
            Long start = (Long) request.getAttribute(START_TIME);
            long tookMs = start != null ? (System.nanoTime() - start) / 1_000_000 : -1;

            String method = request.getMethod();
            String uri = request.getRequestURI();
            String query = request.getQueryString();
            int status = response.getStatus();
            String ip = clientIp(request);
            String userId = currentUserId();
            String traceId = MDC.get("traceId");

            log.info("[HTTP] {} {}{} -> {} {}ms ip={} user={} traceId={}",
                    method, uri, query != null ? "?" + query : "",
                    status, tookMs, ip, userId, traceId);

        } finally {
            MDC.remove("traceId");
        }
    }

    private String clientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(xff)) return xff.split(",")[0].trim();
        String xr = request.getHeader("X-Real-IP");
        if (StringUtils.hasText(xr)) return xr;
        return request.getRemoteAddr();
    }

    private String currentUserId() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
                return String.valueOf(auth.getName());
            }
        } catch (Exception ignored) {}
        return "anon";
    }

    private String getOrCreateTraceId(HttpServletRequest request) {
        String fromHeader = request.getHeader("X-Trace-Id");
        return StringUtils.hasText(fromHeader) ? fromHeader : UUID.randomUUID().toString();
    }
}
