package com.br.foodconnect.service;

import com.br.foodconnect.util.JsonCacheData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void saveValidationCode(String email, String code, int expirationMinutes) {

        JsonCacheData data = new JsonCacheData(email, code, expirationMinutes);

        try {

            String jsonData = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(email, jsonData, expirationMinutes, TimeUnit.MINUTES);

        } catch (JsonProcessingException e) {

                throw new RuntimeException("Error on convert JSON in Object", e);
        }
    }

    public JsonCacheData getValidationCode(String email) {

        String jsonData = redisTemplate.opsForValue().get(email);

        try {

            return objectMapper.readValue(jsonData, JsonCacheData.class);

        } catch (JsonProcessingException e) {

            throw new RuntimeException("Error on convert JSON in Object", e);
        }
    }

    public void removeValidationCode(String email) {
        redisTemplate.delete(email);
    }

    public String getValueFromCache(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }
}
