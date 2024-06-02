package com.foundation.emailer.controller;

import com.foundation.emailer.dto.EmailTemplateDTO;
import com.foundation.emailer.service.EmailTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailTemplateControllerTest {

    @Mock
    private EmailTemplateService emailTemplateService;

    @InjectMocks
    private EmailTemplateController emailTemplateController;

    private EmailTemplateDTO emailTemplateDTO;

    @BeforeEach
    void setUp() {
        emailTemplateDTO = new EmailTemplateDTO();
        emailTemplateDTO.setId(1L);
        emailTemplateDTO.setName("Test Template");
        emailTemplateDTO.setTemplateText("Test Template Text");
    }

    @Test
    @DisplayName("Create Email Template")
    void createEmailTemplate() {
        when(emailTemplateService.createEmailTemplate(any(EmailTemplateDTO.class))).thenReturn(emailTemplateDTO);

        ResponseEntity<EmailTemplateDTO> response = emailTemplateController.createEmailTemplate(emailTemplateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(emailTemplateDTO, response.getBody());
        verify(emailTemplateService, times(1)).createEmailTemplate(any(EmailTemplateDTO.class));
    }

    @Test
    @DisplayName("Update Email Template")
    void updateEmailTemplate() {
        when(emailTemplateService.updateEmailTemplate(eq(1L), any(EmailTemplateDTO.class))).thenReturn(emailTemplateDTO);

        ResponseEntity<EmailTemplateDTO> response = emailTemplateController.updateEmailTemplate(1L, emailTemplateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailTemplateDTO, response.getBody());
        verify(emailTemplateService, times(1)).updateEmailTemplate(eq(1L), any(EmailTemplateDTO.class));
    }

    @Test
    @DisplayName("Delete Email Template")
    void deleteEmailTemplate() {
        ResponseEntity<Void> response = emailTemplateController.deleteEmailTemplate(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(emailTemplateService, times(1)).deleteEmailTemplate(eq(1L));
    }

    @Test
    @DisplayName("Get Email Template by Id - Found")
    void getEmailTemplateByIdFound() {
        when(emailTemplateService.findEmailTemplate(eq(1L))).thenReturn(Optional.of(emailTemplateDTO));

        ResponseEntity<EmailTemplateDTO> response = emailTemplateController.findEmailTemplateById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailTemplateDTO, response.getBody());
        verify(emailTemplateService, times(1)).findEmailTemplate(eq(1L));
    }

    @Test
    @DisplayName("Get Email Template by Id - Not Found")
    void getEmailTemplateByIdNotFound() {
        when(emailTemplateService.findEmailTemplate(eq(1L))).thenReturn(Optional.empty());

        ResponseEntity<EmailTemplateDTO> response = emailTemplateController.findEmailTemplateById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(emailTemplateService, times(1)).findEmailTemplate(eq(1L));
    }

    @Test
    @DisplayName("Get All Email Templates")
    void getAllEmailTemplates() {
        Page<EmailTemplateDTO> emailTemplatePage = new PageImpl<>(Collections.singletonList(emailTemplateDTO));
        when(emailTemplateService.getAllEmailTemplates(eq(0), eq(10), eq("id"), eq("asc"))).thenReturn(emailTemplatePage);

        ResponseEntity<Page<EmailTemplateDTO>> response = emailTemplateController.getAllEmailTemplates(0, 10, "id", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailTemplatePage, response.getBody());
        verify(emailTemplateService, times(1)).getAllEmailTemplates(eq(0), eq(10), eq("id"), eq("asc"));
    }
}
