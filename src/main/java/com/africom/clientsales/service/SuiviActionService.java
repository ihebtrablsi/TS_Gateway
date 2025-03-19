package com.africom.clientsales.service;

import com.africom.clientsales.domain.SuiviAction;
import com.africom.clientsales.repository.SuiviActionRepository;
import com.africom.clientsales.service.dto.SuiviActionDTO;
import com.africom.clientsales.service.mapper.SuiviActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SuiviAction}.
 */
@Service
@Transactional
public class SuiviActionService {

    private final Logger log = LoggerFactory.getLogger(SuiviActionService.class);

    private final SuiviActionRepository suiviActionRepository;

    private final SuiviActionMapper suiviActionMapper;

    public SuiviActionService(SuiviActionRepository suiviActionRepository, SuiviActionMapper suiviActionMapper) {
        this.suiviActionRepository = suiviActionRepository;
        this.suiviActionMapper = suiviActionMapper;
    }

    /**
     * Save a suiviAction.
     *
     * @param suiviActionDTO the entity to save.
     * @return the persisted entity.
     */
    public SuiviActionDTO save(SuiviActionDTO suiviActionDTO) {
        log.debug("Request to save SuiviAction : {}", suiviActionDTO);
        SuiviAction suiviAction = suiviActionMapper.toEntity(suiviActionDTO);
        suiviAction = suiviActionRepository.save(suiviAction);
        return suiviActionMapper.toDto(suiviAction);
    }

    /**
     * Get all the suiviActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SuiviActionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SuiviActions");
        return suiviActionRepository.findAll(pageable)
            .map(suiviActionMapper::toDto);
    }


    /**
     * Get one suiviAction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SuiviActionDTO> findOne(Long id) {
        log.debug("Request to get SuiviAction : {}", id);
        return suiviActionRepository.findById(id)
            .map(suiviActionMapper::toDto);
    }

    /**
     * Delete the suiviAction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SuiviAction : {}", id);
        suiviActionRepository.deleteById(id);
    }
}
