package com.abhir.chat.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class PresenceService {

    private final StringRedisTemplate redisTemplate;

    private static final String PREFIX = "user:online:";

    public void setOnline(String username) {
        redisTemplate.opsForValue().set(PREFIX + username, "1", Duration.ofMinutes(5));
    }

    public void setOffline(String username) {
        redisTemplate.delete(PREFIX + username);
    }

    public boolean isOnline(String username) {
        return redisTemplate.hasKey(PREFIX + username);
    }
}
