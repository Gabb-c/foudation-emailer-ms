package com.foundation.emailer.service;

import com.foundation.emailer.dto.SentEmailDTO;
import com.foundation.emailer.model.SentEmail;
import com.foundation.emailer.repository.SentEmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SentEmailServiceTest {

    @Mock
    private SentEmailRepository sentEmailRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SentEmailService sentEmailService;

    private SentEmail sentEmail;
    private SentEmailDTO sentEmailDTO;

    @BeforeEach
    void setUp() {
        sentEmail = new SentEmail();
        sentEmail.setId(1L);
        sentEmail.setNonprofit(null);
        sentEmail.setSentAt(LocalDateTime.now());
        sentEmail.setContent("Test content");

        sentEmailDTO = new SentEmailDTO();
        sentEmailDTO.setId(1L);
        sentEmailDTO.setNonprofit(null);
        sentEmailDTO.setSentAt(LocalDateTime.now());
        sentEmailDTO.setContent("Test content");
    }

    @Test
    @DisplayName("Test findSentEmail - Success")
    void testFindSentEmail_Success() {
        when(sentEmailRepository.findById(1L)).thenReturn(Optional.of(sentEmail));
        when(modelMapper.map(any(SentEmail.class), eq(SentEmailDTO.class))).thenReturn(sentEmailDTO);

        Optional<SentEmailDTO> foundSentEmail = sentEmailService.findSentEmail(1L);

        assertEquals(sentEmailDTO, foundSentEmail.orElse(null));
        verify(sentEmailRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test findSentEmail - Not Found")
    void testFindSentEmail_NotFound() {
        when(sentEmailRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<SentEmailDTO> foundSentEmail = sentEmailService.findSentEmail(1L);

        assertEquals(Optional.empty(), foundSentEmail);
        verify(sentEmailRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test getAllSentEmails - Success")
    void testGetAllSentEmails_Success() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<SentEmail> sentEmailPage = new PageImpl<>(Collections.singletonList(sentEmail), pageable, 1);
        when(sentEmailRepository.findAll(pageable)).thenReturn(sentEmailPage);
        when(modelMapper.map(any(SentEmail.class), eq(SentEmailDTO.class))).thenReturn(sentEmailDTO);

        Page<SentEmailDTO> sentEmailDTOPage = sentEmailService.getAllSentEmails(0, 10, "id", "asc");

        assertEquals(1, sentEmailDTOPage.getTotalElements());
        assertEquals(sentEmailDTO, sentEmailDTOPage.getContent().getFirst());
        verify(sentEmailRepository, times(1)).findAll(pageable);
    }
}
