package com.foundation.emailer.controller;

import com.foundation.emailer.dto.SentEmailDTO;
import com.foundation.emailer.service.SentEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/sent-emails")
@RequiredArgsConstructor
public class SentEmailController {
    private final SentEmailService sentEmailService;

    @GetMapping("/{id}")
    public ResponseEntity<SentEmailDTO> getSentEmailById(@PathVariable Long id) {
        Optional<SentEmailDTO> sentEmail = sentEmailService.findSentEmail(id);
        return sentEmail.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<SentEmailDTO>> getAllSentEmails(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortOrder) {
        Page<SentEmailDTO> sentEmails = sentEmailService.getAllSentEmails(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(sentEmails);
    }
}
