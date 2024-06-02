package com.foundation.emailer.service;

import com.foundation.emailer.dto.EmailTemplateDTO;
import com.foundation.emailer.model.EmailTemplate;
import com.foundation.emailer.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;
    private final ModelMapper modelMapper;

    public EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
        emailTemplateDTO.setId(null);
        EmailTemplate emailTemplate = dtoToEntity(emailTemplateDTO);
        return entityToDTO(emailTemplateRepository.save(emailTemplate));
    }

    public EmailTemplateDTO updateEmailTemplate(Long id, EmailTemplateDTO emailTemplateDTO) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Nonprofit not found with id: " + id));

        emailTemplate.setName(emailTemplateDTO.getName());
        emailTemplate.setTemplateText(emailTemplateDTO.getTemplateText());

        return entityToDTO(emailTemplateRepository.save(emailTemplate));
    }

    public void deleteEmailTemplate(Long id) {
        emailTemplateRepository.deleteById(id);
    }

    public Optional<EmailTemplateDTO> findEmailTemplate(Long id) {
        return emailTemplateRepository.findById(id)
            .map(this::entityToDTO);
    }

    public Page<EmailTemplateDTO> getAllEmailTemplates(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return emailTemplateRepository.findAll(pageable)
            .map(this::entityToDTO);
    }

    public EmailTemplateDTO entityToDTO(EmailTemplate emailTemplate) {
        return modelMapper.map(emailTemplate, EmailTemplateDTO.class);
    }

    public EmailTemplate dtoToEntity(EmailTemplateDTO emailTemplateDTO) {
        return modelMapper.map(emailTemplateDTO, EmailTemplate.class);
    }
}
