package com.africom.clientsales.service;

import com.africom.clientsales.domain.ActionCommerciale;
import com.africom.clientsales.repository.ActionCommercialeRepository;
import com.africom.clientsales.service.dto.ActionCommercialeDTO;
import com.africom.clientsales.service.mapper.ActionCommercialeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActionCommerciale}.
 */
@Service
@Transactional
public class ActionCommercialeService {

    private final Logger log = LoggerFactory.getLogger(ActionCommercialeService.class);

    private final ActionCommercialeRepository actionCommercialeRepository;

    private final ActionCommercialeMapper actionCommercialeMapper;

    public ActionCommercialeService(ActionCommercialeRepository actionCommercialeRepository, ActionCommercialeMapper actionCommercialeMapper) {
        this.actionCommercialeRepository = actionCommercialeRepository;
        this.actionCommercialeMapper = actionCommercialeMapper;
    }

    /**
     * Save a actionCommerciale.
     *
     * @param actionCommercialeDTO the entity to save.
     * @return the persisted entity.
     */
    public ActionCommercialeDTO save(ActionCommercialeDTO actionCommercialeDTO) {
        log.debug("Request to save ActionCommerciale : {}", actionCommercialeDTO);
        ActionCommerciale actionCommerciale = actionCommercialeMapper.toEntity(actionCommercialeDTO);
        actionCommerciale = actionCommercialeRepository.save(actionCommerciale);
        return actionCommercialeMapper.toDto(actionCommerciale);
    }

    /**
     * Get all the actionCommerciales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActionCommercialeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActionCommerciales");
        return actionCommercialeRepository.findAll(pageable)
            .map(actionCommercialeMapper::toDto);
    }


    /**
     * Get one actionCommerciale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActionCommercialeDTO> findOne(Long id) {
        log.debug("Request to get ActionCommerciale : {}", id);
        return actionCommercialeRepository.findById(id)
            .map(actionCommercialeMapper::toDto);
    }

    /**
     * Delete the actionCommerciale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActionCommerciale : {}", id);
        actionCommercialeRepository.deleteById(id);
    }
}
