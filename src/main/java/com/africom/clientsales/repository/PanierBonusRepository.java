package com.africom.clientsales.repository;

import com.africom.clientsales.domain.PanierBonus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PanierBonus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PanierBonusRepository extends JpaRepository<PanierBonus, Long> {
}
