package com.br.foodconnect.controller;

import com.br.foodconnect.service.EmailService;
import com.br.foodconnect.service.GenerateCodeService;
import com.br.foodconnect.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-email")
public class EmailController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GenerateCodeService generateCodeService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendValidationCode(@RequestParam String email) {
        String code = generateCodeService.generateRandomCode();
        redisService.saveValidationCode(email, code, 10);
        emailService.sendConfirmationEmail(email, code);
        return ResponseEntity.status(201)
                .body(String.format("Confirmation code send to %s with the code %s", email, code));
    }
}
