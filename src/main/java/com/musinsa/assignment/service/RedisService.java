package com.musinsa.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
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
    public void delete(String key) {
        CompletableFuture.completedFuture(redisTemplate.delete(key));
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Map<Object, Object>> getAllHashEntries(String key) {
        return CompletableFuture.completedFuture(redisTemplate.opsForHash().entries(key));
    }

    @Async("threadPoolTaskExecutor")
    public void addHashMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> hashFieldHashMap(String key, String field) {
        return CompletableFuture.completedFuture(redisTemplate.opsForHash().hasKey(key, field));
    }

    @Async("threadPoolTaskExecutor")
    public void deleteHashMap(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Object> getHashMap(String key, String field) {
        return CompletableFuture.completedFuture(redisTemplate.opsForHash().get(key, field));
    }
}
