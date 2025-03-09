package com.hcl.mortgagechecker.repository;

import com.hcl.mortgagechecker.model.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *  Repository to interact with data layer for InterestRate entity
 */
@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {
    List<InterestRate> findByMaturityPeriodOrderByLastUpdateDesc(Integer maturityPeriod);
}
