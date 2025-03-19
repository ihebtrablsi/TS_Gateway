package com.africom.productinventory.repository;

import com.africom.productinventory.domain.AuditStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AuditStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditStockRepository extends JpaRepository<AuditStock, Long> {
}
