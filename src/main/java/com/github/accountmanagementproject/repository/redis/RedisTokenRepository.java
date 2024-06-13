package com.github.accountmanagementproject.repository.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisTokenRepository{

    private final ValueOperations<String, String> jwtValueOperations;
    public RedisTokenRepository(RedisTemplate<String, String> redisTemplate) {
        this.jwtValueOperations = redisTemplate.opsForValue();
    }


    public void tokenSave(String accessToken, String refreshToken, Duration exp){
        jwtValueOperations.set(accessToken, refreshToken, exp);
    }
    public String getAndDeleteJwtEntity(String accessToken){
        return jwtValueOperations.getAndDelete(accessToken);
    }
    public String deleteTest(String key){
        return jwtValueOperations.getAndDelete(key);
    }

}