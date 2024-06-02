package com.foundation.emailer.repository;

import com.foundation.emailer.model.Nonprofit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonprofitRepository extends JpaRepository<Nonprofit, Long> {
}
