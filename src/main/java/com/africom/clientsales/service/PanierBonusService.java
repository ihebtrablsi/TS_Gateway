package com.africom.clientsales.service;

import com.africom.clientsales.domain.PanierBonus;
import com.africom.clientsales.repository.PanierBonusRepository;
import com.africom.clientsales.service.dto.PanierBonusDTO;
import com.africom.clientsales.service.mapper.PanierBonusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PanierBonus}.
 */
@Service
@Transactional
public class PanierBonusService {

    private final Logger log = LoggerFactory.getLogger(PanierBonusService.class);

    private final PanierBonusRepository panierBonusRepository;

    private final PanierBonusMapper panierBonusMapper;

    public PanierBonusService(PanierBonusRepository panierBonusRepository, PanierBonusMapper panierBonusMapper) {
        this.panierBonusRepository = panierBonusRepository;
        this.panierBonusMapper = panierBonusMapper;
    }

    /**
     * Save a panierBonus.
     *
     * @param panierBonusDTO the entity to save.
     * @return the persisted entity.
     */
    public PanierBonusDTO save(PanierBonusDTO panierBonusDTO) {
        log.debug("Request to save PanierBonus : {}", panierBonusDTO);
        PanierBonus panierBonus = panierBonusMapper.toEntity(panierBonusDTO);
        panierBonus = panierBonusRepository.save(panierBonus);
        return panierBonusMapper.toDto(panierBonus);
    }

    /**
     * Get all the panierBonuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PanierBonusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PanierBonuses");
        return panierBonusRepository.findAll(pageable)
            .map(panierBonusMapper::toDto);
    }


    /**
     * Get one panierBonus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PanierBonusDTO> findOne(Long id) {
        log.debug("Request to get PanierBonus : {}", id);
        return panierBonusRepository.findById(id)
            .map(panierBonusMapper::toDto);
    }

    /**
     * Delete the panierBonus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PanierBonus : {}", id);
        panierBonusRepository.deleteById(id);
    }
}
