package com.foundation.emailer.repository;

import com.foundation.emailer.model.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {
}
