package com.br.foodconnect.util;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ValidationCodeUtils implements Serializable {

    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public ValidationCodeUtils(String code, int expirationMinutes) {
        this.code = code;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(expirationMinutes);
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
