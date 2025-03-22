package com.br.foodconnect.controller;

import com.br.foodconnect.repository.CustomerCredentialRepository;
import com.br.foodconnect.service.EmailService;
import com.br.foodconnect.service.GenerateCodeService;
import com.br.foodconnect.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send-email")
@CrossOrigin("*")
public class EmailController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GenerateCodeService generateCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerCredentialRepository credentialRepository;

    @PostMapping
    public ResponseEntity<String> sendValidationCode(@RequestParam String email) {

        try {
            if (credentialRepository.emailExists(email)) {
                return ResponseEntity.status(409).body("This email already exists.");
            }
            String code = generateCodeService.generateRandomCode();
            redisService.saveValidationCode(email, code, 10);
            emailService.sendConfirmationEmail(email, code);

            return ResponseEntity.status(201)
                    .body(String.format("Confirmation code send to %s with the code %s", email, code));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Failed to send email and save the validation code. Please try again.");
        }
    }

    @GetMapping("/teste")
    public String ola() {
        return "Greeting";
    }
}
