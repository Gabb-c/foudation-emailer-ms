package com.foundation.emailer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MockEmailService {
    public void sendEmail(String to, String body) {
        log.info("Sending email to: {}", to);
        log.info("Successfully sent email to: {}", to);
    }
}
