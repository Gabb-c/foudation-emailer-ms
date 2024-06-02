package com.foundation.emailer.controller;

import com.foundation.emailer.dto.EmailTemplateDTO;
import com.foundation.emailer.service.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/email-templates")
@RequiredArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @PostMapping
    public ResponseEntity<EmailTemplateDTO> createEmailTemplate(@RequestBody EmailTemplateDTO emailTemplateDTO) {
        EmailTemplateDTO createdEmailTemplate = emailTemplateService.createEmailTemplate(emailTemplateDTO);
        return new ResponseEntity<>(createdEmailTemplate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> updateEmailTemplate(@PathVariable Long id, @RequestBody EmailTemplateDTO updatedEmailTemplate) {
        EmailTemplateDTO updatedEntity = emailTemplateService.updateEmailTemplate(id, updatedEmailTemplate);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailTemplate(@PathVariable Long id) {
        emailTemplateService.deleteEmailTemplate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> findEmailTemplateById(@PathVariable Long id) {
        Optional<EmailTemplateDTO> emailTemplate = emailTemplateService.findEmailTemplate(id);
        return emailTemplate.map(ResponseEntity::ok)
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Page<EmailTemplateDTO>> getAllEmailTemplates(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortOrder) {
        Page<EmailTemplateDTO> emailTemplates = emailTemplateService.getAllEmailTemplates(page, size, sortBy, sortOrder);
        return new ResponseEntity<>(emailTemplates, HttpStatus.OK);
    }
}
