package com.musinsa.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> exist(String key) {
        return CompletableFuture.completedFuture(redisTemplate.hasKey(key));
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> get(String key) {
        return CompletableFuture.completedFuture(redisTemplate.opsForValue().get(key));
    }

    @Async("threadPoolTaskExecutor")
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Async("threadPoolTaskExecutor")
    public void unlink(String key) {
        redisTemplate.unlink(key);
    }

    @Async("threadPoolTaskExecutor")
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Set<String>> getSet(String key) {
        return CompletableFuture.completedFuture(redisTemplate.opsForSet().members(key));
    }
}
