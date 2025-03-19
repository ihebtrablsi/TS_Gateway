package com.africom.finance.service;

import com.africom.finance.domain.Lettrage;
import com.africom.finance.repository.LettrageRepository;
import com.africom.finance.service.dto.LettrageDTO;
import com.africom.finance.service.mapper.LettrageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Lettrage}.
 */
@Service
@Transactional
public class LettrageService {

    private final Logger log = LoggerFactory.getLogger(LettrageService.class);

    private final LettrageRepository lettrageRepository;

    private final LettrageMapper lettrageMapper;

    public LettrageService(LettrageRepository lettrageRepository, LettrageMapper lettrageMapper) {
        this.lettrageRepository = lettrageRepository;
        this.lettrageMapper = lettrageMapper;
    }

    /**
     * Save a lettrage.
     *
     * @param lettrageDTO the entity to save.
     * @return the persisted entity.
     */
    public LettrageDTO save(LettrageDTO lettrageDTO) {
        log.debug("Request to save Lettrage : {}", lettrageDTO);
        Lettrage lettrage = lettrageMapper.toEntity(lettrageDTO);
        lettrage = lettrageRepository.save(lettrage);
        return lettrageMapper.toDto(lettrage);
    }

    /**
     * Get all the lettrages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LettrageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lettrages");
        return lettrageRepository.findAll(pageable)
            .map(lettrageMapper::toDto);
    }


    /**
     * Get one lettrage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LettrageDTO> findOne(Long id) {
        log.debug("Request to get Lettrage : {}", id);
        return lettrageRepository.findById(id)
            .map(lettrageMapper::toDto);
    }

    /**
     * Delete the lettrage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lettrage : {}", id);
        lettrageRepository.deleteById(id);
    }
}
