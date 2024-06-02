package com.foundation.emailer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SentEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nonprofit_id", nullable = false)
    private Nonprofit nonprofit;

    private LocalDateTime sentAt;

    @Lob
    private String content;

}
