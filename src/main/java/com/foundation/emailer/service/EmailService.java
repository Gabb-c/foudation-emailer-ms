package com.foundation.emailer.service;

import com.foundation.emailer.dto.NonprofitDTO;
import com.foundation.emailer.dto.SentEmailDTO;
import com.foundation.emailer.model.Nonprofit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final ModelMapper modelMapper;
    private final SentEmailService sentEmailService;
    private final NonprofitService nonprofitService;

    public SentEmailDTO sendEmail(Long to, String body) {
        log.info("Sending email to: {}", to);
        SentEmailDTO sentEmailDTO = new SentEmailDTO();

        Nonprofit nonprofit = nonprofitService.getNonprofitById(to)
            .map((element) -> modelMapper.map(element, Nonprofit.class))
            .orElseThrow(() -> new IllegalArgumentException(""));

        sentEmailDTO.setContent(body);
        sentEmailDTO.setSentAt(LocalDateTime.now());
        sentEmailDTO.setNonprofit(nonprofit);
        log.info("Successfully sent email to: {}", to);

        return sentEmailService.createSentEmail(sentEmailDTO);
    }
}
