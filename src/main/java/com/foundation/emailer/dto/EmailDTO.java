package com.foundation.emailer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailDTO implements Serializable {
    private Long nonprofitId;
    private String body;
}
