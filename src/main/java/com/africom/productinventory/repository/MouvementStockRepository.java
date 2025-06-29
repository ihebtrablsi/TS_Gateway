package com.africom.productinventory.repository;

import com.africom.productinventory.domain.MouvementStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the MouvementStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    @Query("SELECT SUM(m.quantite) FROM MouvementStock m WHERE m.produit.id = :produitId")
    Optional<Long> sumQuantiteByProduit(@Param("produitId") Long produitId);
    int countByProduitIdAndTypeMouvement(Long produitId, String typeMouvement);
}
