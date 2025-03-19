package com.africom.finance.repository;

import com.africom.finance.domain.Paiement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Paiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
