package com.foundation.emailer.repository;

import com.foundation.emailer.model.Nonprofit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonprofitRepository extends PagingAndSortingRepository<Nonprofit, Long> {
}
