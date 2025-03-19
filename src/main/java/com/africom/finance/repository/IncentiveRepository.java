package com.africom.finance.repository;

import com.africom.finance.domain.Incentive;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Incentive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncentiveRepository extends JpaRepository<Incentive, Long> {
}
