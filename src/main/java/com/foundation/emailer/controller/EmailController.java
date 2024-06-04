package com.foundation.emailer.controller;

import com.foundation.emailer.dto.EmailDTO;
import com.foundation.emailer.dto.SentEmailDTO;
import com.foundation.emailer.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<SentEmailDTO> sendEmail(@RequestBody EmailDTO emailDTO) {
        SentEmailDTO sentEmailDTO = emailService.sendEmail(emailDTO.getNonprofitId(), emailDTO.getBody());
        return new ResponseEntity<>(sentEmailDTO, HttpStatus.CREATED);
    }
}
