package com.africom.finance.repository;

import com.africom.finance.domain.Ristourne;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Ristourne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RistourneRepository extends JpaRepository<Ristourne, Long> {
}
