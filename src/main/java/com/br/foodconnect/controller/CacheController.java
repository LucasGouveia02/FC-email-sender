package com.br.foodconnect.controller;

import com.br.foodconnect.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cache")
@CrossOrigin("*")
public class CacheController {

    @Autowired
    private RedisService redisService;

    @GetMapping("key")
    public String getCacheValue(@RequestParam String key) {
        return redisService.getValueFromCache(key);
    }

    @GetMapping("all-keys")
    public Set<String> getCacheKeys() {
        return redisService.getAllKeys();
    }
}
