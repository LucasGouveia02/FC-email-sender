package com.br.foodconnect.service;

import com.br.foodconnect.util.ValidationCodeUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveValidationCode(String email, String code, int expirationMinutes) {
        ValidationCodeUtils validationCodeUtils = new ValidationCodeUtils(code, expirationMinutes);
        redisTemplate.opsForValue().set(email, validationCodeUtils, expirationMinutes, TimeUnit.MINUTES);
    }

    public ValidationCodeUtils getValidationCode(String email) {
        return (ValidationCodeUtils) redisTemplate.opsForValue().get(email);
    }

    public void removeValidationCode(String email) {
        redisTemplate.delete(email);
    }
}
