package com.foundation.emailer.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class NonprofitDTO implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String email;
}
