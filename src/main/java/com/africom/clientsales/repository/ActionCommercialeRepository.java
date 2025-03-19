package com.africom.clientsales.repository;

import com.africom.clientsales.domain.ActionCommerciale;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActionCommerciale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionCommercialeRepository extends JpaRepository<ActionCommerciale, Long> {
}
