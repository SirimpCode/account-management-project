package com.github.accountmanagementproject.repository.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisRepository {

    private final ValueOperations<String, String> valueOperations;
    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }


    public void save(String key, String value, Duration exp){
        valueOperations.set(key, value, exp);
    }
    public String getValue(String key){
        return valueOperations.get(key);
    }
    public String getAndDeleteValue(String key){
        return valueOperations.getAndDelete(key);
    }
    public String deleteTest(String key){
        return valueOperations.getAndDelete(key);
    }

}