package com.br.foodconnect.controller;

import com.br.foodconnect.dto.ValidationRequest;
import com.br.foodconnect.service.ValidationCodeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/code-validation")
public class ValidationCodeController {

    @Autowired
    private ValidationCodeService validationCodeService;

    @PostMapping
    public ResponseEntity<String> validationCode(@RequestBody ValidationRequest request)
            throws JsonProcessingException {
        return validationCodeService.validateCode(request);
    }
}
