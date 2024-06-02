package com.foundation.emailer.repository;

import com.foundation.emailer.model.EmailTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailTemplateRepository extends PagingAndSortingRepository<EmailTemplate, Long> {
}
