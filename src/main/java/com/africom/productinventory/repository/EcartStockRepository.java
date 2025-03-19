package com.africom.productinventory.repository;

import com.africom.productinventory.domain.EcartStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EcartStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcartStockRepository extends JpaRepository<EcartStock, Long> {
}
