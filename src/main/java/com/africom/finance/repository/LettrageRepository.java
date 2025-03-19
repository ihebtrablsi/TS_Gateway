package com.africom.finance.repository;

import com.africom.finance.domain.Lettrage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lettrage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LettrageRepository extends JpaRepository<Lettrage, Long> {
}
