package com.foundation.emailer.repository;

import com.foundation.emailer.model.SentEmail;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SentEmailRepository extends PagingAndSortingRepository<SentEmail, Long> {
}
