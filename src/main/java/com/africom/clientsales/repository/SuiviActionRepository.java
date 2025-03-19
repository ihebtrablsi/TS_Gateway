package com.africom.clientsales.repository;

import com.africom.clientsales.domain.SuiviAction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SuiviAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuiviActionRepository extends JpaRepository<SuiviAction, Long> {
}
