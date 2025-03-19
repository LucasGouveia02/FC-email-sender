package com.br.foodconnect.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateCodeService {

    private final Random random = new Random();

    public String generateRandomCode() {
        return String.format("%04d", random.nextInt(1000));
    }
}
