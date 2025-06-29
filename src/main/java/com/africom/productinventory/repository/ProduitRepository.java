package com.africom.productinventory.repository;

import com.africom.productinventory.domain.Produit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByFamilleId(Long familleId);

    List<Produit> findAllByFamilleId(Long familleId);

    List<Produit> findByStockLessThan(Integer seuil);
}
