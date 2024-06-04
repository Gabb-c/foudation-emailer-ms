package com.foundation.emailer.service;

import com.foundation.emailer.dto.SentEmailDTO;
import com.foundation.emailer.model.SentEmail;
import com.foundation.emailer.repository.SentEmailRepository;
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
public class SentEmailService {
    private final SentEmailRepository sentEmailRepository;
    private final ModelMapper modelMapper;

    public SentEmailDTO createSentEmail(SentEmailDTO sentEmailDTO) {
        sentEmailDTO.setId(null);
        SentEmail sentEmail = dtoToEntity(sentEmailDTO);
        return entityToDTO(sentEmailRepository.save(sentEmail));
    }

    public Optional<SentEmailDTO> findSentEmail(Long id) {
        return sentEmailRepository.findById(id)
            .map(this::entityToDTO);
    }

    public Page<SentEmailDTO> getAllSentEmails(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return sentEmailRepository.findAll(pageable)
            .map(this::entityToDTO);
    }

    public SentEmailDTO entityToDTO(SentEmail sentEmail) {
        return modelMapper.map(sentEmail, SentEmailDTO.class);
    }

    public SentEmail dtoToEntity(SentEmailDTO sentEmailDTO) {
        return modelMapper.map(sentEmailDTO, SentEmail.class);
    }
}
