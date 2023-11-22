package com.crm.controller;

import com.crm.payload.EmailDto;
import com.crm.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @PostMapping
    public ResponseEntity<EmailDto> sendEmail(@RequestBody EmailDto emailDto){
        EmailDto dto = emailService.sendEmail(emailDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
