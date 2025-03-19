package com.africom.productinventory.repository;

import com.africom.productinventory.domain.FamilleProduit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FamilleProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilleProduitRepository extends JpaRepository<FamilleProduit, Long> {
}
