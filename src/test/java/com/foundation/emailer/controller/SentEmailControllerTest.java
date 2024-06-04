package com.foundation.emailer.controller;

import com.foundation.emailer.dto.SentEmailDTO;
import com.foundation.emailer.service.SentEmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SentEmailControllerTest {

    @Mock
    private SentEmailService sentEmailService;

    @InjectMocks
    private SentEmailController sentEmailController;

    @Test
    @DisplayName("Get Sent Email by ID - Found")
    void getSentEmailByIdFound() {
        long id = 1L;
        SentEmailDTO sentEmailDTO = new SentEmailDTO();
        sentEmailDTO.setId(id);
        when(sentEmailService.findSentEmail(id)).thenReturn(Optional.of(sentEmailDTO));

        ResponseEntity<SentEmailDTO> responseEntity = sentEmailController.getSentEmailById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sentEmailDTO, responseEntity.getBody());
        verify(sentEmailService, times(1)).findSentEmail(id);
    }

    @Test
    @DisplayName("Get Sent Email by ID - Not Found")
    void getSentEmailByIdNotFound() {
        long id = 1L;
        when(sentEmailService.findSentEmail(id)).thenReturn(Optional.empty());

        ResponseEntity<SentEmailDTO> responseEntity = sentEmailController.getSentEmailById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(sentEmailService, times(1)).findSentEmail(id);
    }

    @Test
    @DisplayName("Get All Sent Emails")
    void getAllSentEmails() {
        int page = 0;
        int size = 10;
        String sortBy = "id";
        String sortOrder = "asc";

        Page<SentEmailDTO> sentEmailPage = Page.empty();
        when(sentEmailService.getAllSentEmails(page, size, sortBy, sortOrder)).thenReturn(sentEmailPage);

        ResponseEntity<Page<SentEmailDTO>> responseEntity = sentEmailController.getAllSentEmails(page, size, sortBy, sortOrder);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sentEmailPage, responseEntity.getBody());
        verify(sentEmailService, times(1)).getAllSentEmails(page, size, sortBy, sortOrder);
    }
}
