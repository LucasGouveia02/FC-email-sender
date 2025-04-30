package com.br.foodconnect.service;

import com.br.foodconnect.dto.ValidationRequest;
import com.br.foodconnect.util.JsonCacheData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ValidationCodeService {

    @Autowired
    private RedisService redisService;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public ValidationCodeService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<String> validateCode(ValidationRequest request) throws JsonProcessingException {

        try {
            String data = redisTemplate.opsForValue().get(request.getEmail());

            if (data == null) {
                return ResponseEntity.status(404).body("Validation code has expired.");
            }

            JsonCacheData deserializeData = objectMapper.readValue(data, JsonCacheData.class);

            if (deserializeData.getCode().equals(request.getCode())) {
                redisService.clearCacheByKey(request.getEmail());
                return ResponseEntity.status(200)
                        .body(String.format(
                                "The account %s has been validated with the code %s", request.getEmail(), request.getCode()
                        ));
            }

            return ResponseEntity.status(400).body("The provided code is not valid.");

        }  catch (JsonProcessingException e) {

            return ResponseEntity.status(500).body("Error processing validation data.");

        } catch (Exception e) {

            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}
