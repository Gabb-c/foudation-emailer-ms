package com.foundation.emailer.service;

import com.foundation.emailer.dto.EmailTemplateDTO;
import com.foundation.emailer.model.EmailTemplate;
import com.foundation.emailer.repository.EmailTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailTemplateServiceTest {

    @Mock
    private EmailTemplateRepository emailTemplateRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmailTemplateService emailTemplateService;

    private EmailTemplate emailTemplate;
    private EmailTemplateDTO emailTemplateDTO;

    @BeforeEach
    void setUp() {
        emailTemplate = new EmailTemplate();
        emailTemplate.setId(1L);
        emailTemplate.setName("Test Template");
        emailTemplate.setTemplateText("This is a test template");

        emailTemplateDTO = new EmailTemplateDTO();
        emailTemplateDTO.setId(1L);
        emailTemplateDTO.setName("Test Template");
        emailTemplateDTO.setTemplateText("This is a test template");
    }

    @Test
    @DisplayName("Create Email Template")
    void createEmailTemplate() {
        when(modelMapper.map(any(EmailTemplateDTO.class), eq(EmailTemplate.class))).thenReturn(emailTemplate);
        when(emailTemplateRepository.save(any(EmailTemplate.class))).thenReturn(emailTemplate);
        when(modelMapper.map(any(EmailTemplate.class), eq(EmailTemplateDTO.class))).thenReturn(emailTemplateDTO);

        EmailTemplateDTO createdEmailTemplate = emailTemplateService.createEmailTemplate(emailTemplateDTO);

        assertNotNull(createdEmailTemplate);
        assertEquals("Test Template", createdEmailTemplate.getName());
        verify(emailTemplateRepository, times(1)).save(any(EmailTemplate.class));
    }

    @Test
    @DisplayName("Update Email Template")
    void updateEmailTemplate() {
        when(emailTemplateRepository.findById(1L)).thenReturn(Optional.of(emailTemplate));
        when(emailTemplateRepository.save(any(EmailTemplate.class))).thenReturn(emailTemplate);
        when(modelMapper.map(any(EmailTemplate.class), eq(EmailTemplateDTO.class))).thenReturn(emailTemplateDTO);

        EmailTemplateDTO updatedEmailTemplate = emailTemplateService.updateEmailTemplate(1L, emailTemplateDTO);

        assertNotNull(updatedEmailTemplate);
        assertEquals("Test Template", updatedEmailTemplate.getName());
        verify(emailTemplateRepository, times(1)).findById(1L);
        verify(emailTemplateRepository, times(1)).save(any(EmailTemplate.class));
    }

    @Test
    @DisplayName("Delete Email Template")
    void deleteEmailTemplate() {
        doNothing().when(emailTemplateRepository).deleteById(1L);

        emailTemplateService.deleteEmailTemplate(1L);

        verify(emailTemplateRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Find Email Template By Id")
    void findEmailTemplate() {
        when(emailTemplateRepository.findById(1L)).thenReturn(Optional.of(emailTemplate));
        when(modelMapper.map(any(EmailTemplate.class), eq(EmailTemplateDTO.class))).thenReturn(emailTemplateDTO);

        Optional<EmailTemplateDTO> foundEmailTemplate = emailTemplateService.findEmailTemplate(1L);

        assertTrue(foundEmailTemplate.isPresent());
        assertEquals("Test Template", foundEmailTemplate.get().getName());
        verify(emailTemplateRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get All Email Templates")
    void getAllEmailTemplates() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<EmailTemplate> emailTemplatePage = new PageImpl<>(Collections.singletonList(emailTemplate));

        when(emailTemplateRepository.findAll(pageable)).thenReturn(emailTemplatePage);
        when(modelMapper.map(any(EmailTemplate.class), eq(EmailTemplateDTO.class))).thenReturn(emailTemplateDTO);

        Page<EmailTemplateDTO> emailTemplateDTOPage = emailTemplateService.getAllEmailTemplates(0, 10, "id", "asc");

        assertNotNull(emailTemplateDTOPage);
        assertEquals(1, emailTemplateDTOPage.getTotalElements());
        assertEquals("Test Template", emailTemplateDTOPage.getContent().getFirst().getName());
        verify(emailTemplateRepository, times(1)).findAll(pageable);
    }
}
