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
                return ResponseEntity.status(409).body("Este e-mail já está em uso.");
            }
            String code = generateCodeService.generateRandomCode();
            redisService.saveValidationCode(email, code, 10);
            System.out.println("Redis ok. Code: " + code);
            emailService.sendConfirmationEmail(email, code);

            return ResponseEntity.status(201)
                    .body(String.format("Código de validação %s enviado para o e-mail %s.", code, email));
        } catch (Exception e) {
                return ResponseEntity.status(400)
                        .body("Falha ao enviar e-mail e salvar código de validação. Por favor, tente novamente.");
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> sendResetPasswordValidationCode(@RequestParam String email) {
        try {
            String code = generateCodeService.generateRandomCode();
            redisService.saveValidationCode(email, code, 10);
            emailService.sendConfirmationEmail(email, code);

            return ResponseEntity.status(201)
                    .body(String.format("Código de validação enviado para o e-mail %s.", email));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body("Falha ao enviar e-mail e salvar código de validação. Por favor, tente novamente.");
        }
    }

}
