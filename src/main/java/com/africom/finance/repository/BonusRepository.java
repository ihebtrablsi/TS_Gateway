package com.africom.finance.repository;

import com.africom.finance.domain.Bonus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bonus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
}
