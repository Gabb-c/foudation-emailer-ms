package com.foundation.emailer.dto;

import com.foundation.emailer.model.Nonprofit;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SentEmailDTO implements Serializable {
    private Long id;
    private Nonprofit nonprofit;
    private LocalDateTime sentAt;
    private String content;
}
