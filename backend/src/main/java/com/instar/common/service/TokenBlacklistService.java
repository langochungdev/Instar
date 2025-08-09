package com.instar.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String BLACKLIST_KEY = "blacklist_tokens";

    public void blacklistToken(String token, long expirationMillis) {
        redisTemplate.opsForList().rightPush(BLACKLIST_KEY, token);
        redisTemplate.expire(BLACKLIST_KEY, expirationMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        Long size = redisTemplate.opsForList().size(BLACKLIST_KEY);
        if (size == null || size == 0) return false;
        for (int i = 0; i < size; i++) {
            String value = redisTemplate.opsForList().index(BLACKLIST_KEY, i);
            if (token.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
