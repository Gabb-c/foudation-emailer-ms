package com.foundation.emailer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailTemplateDTO implements Serializable {
    private Long id;
    private String name;
    private String templateText;
}
