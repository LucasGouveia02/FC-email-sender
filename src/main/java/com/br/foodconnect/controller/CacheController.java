package com.br.foodconnect.controller;

import com.br.foodconnect.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/cache")
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
