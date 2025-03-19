package com.africom.clientsales.repository;

import com.africom.clientsales.domain.PointDeVente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PointDeVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointDeVenteRepository extends JpaRepository<PointDeVente, Long> {
}
