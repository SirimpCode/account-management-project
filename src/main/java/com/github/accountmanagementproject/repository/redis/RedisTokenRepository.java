package com.github.accountmanagementproject.repository.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository{

    private final RedisTemplate<String, String> redisTemplate;
//    public RedisTokenRepository(RedisTemplate<String, String> redisTemplate) {
//        this.jwtValueOperations = redisTemplate.opsForValue();
//    }


    public void tokenSave(String accessToken, String refreshToken, Duration exp){
        ValueOperations<String, String> jwtValueOperations = redisTemplate.opsForValue();
        jwtValueOperations.set(accessToken, refreshToken, exp);
    }
    public String getAndDeleteJwtEntity(String accessToken){
        ValueOperations<String, String> jwtValueOperations = redisTemplate.opsForValue();
        return jwtValueOperations.getAndDelete(accessToken);
    }
    public String deleteTest(String key){
        ValueOperations<String, String> jwtValueOperations = redisTemplate.opsForValue();
        return jwtValueOperations.getAndDelete(key);
    }

}