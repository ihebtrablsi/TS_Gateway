package com.africom.finance.service;

import com.africom.finance.domain.Ristourne;
import com.africom.finance.repository.RistourneRepository;
import com.africom.finance.service.dto.RistourneDTO;
import com.africom.finance.service.mapper.RistourneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ristourne}.
 */
@Service
@Transactional
public class RistourneService {

    private final Logger log = LoggerFactory.getLogger(RistourneService.class);

    private final RistourneRepository ristourneRepository;

    private final RistourneMapper ristourneMapper;

    public RistourneService(RistourneRepository ristourneRepository, RistourneMapper ristourneMapper) {
        this.ristourneRepository = ristourneRepository;
        this.ristourneMapper = ristourneMapper;
    }

    /**
     * Save a ristourne.
     *
     * @param ristourneDTO the entity to save.
     * @return the persisted entity.
     */
    public RistourneDTO save(RistourneDTO ristourneDTO) {
        log.debug("Request to save Ristourne : {}", ristourneDTO);
        Ristourne ristourne = ristourneMapper.toEntity(ristourneDTO);
        ristourne = ristourneRepository.save(ristourne);
        return ristourneMapper.toDto(ristourne);
    }

    /**
     * Get all the ristournes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RistourneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ristournes");
        return ristourneRepository.findAll(pageable)
            .map(ristourneMapper::toDto);
    }


    /**
     * Get one ristourne by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RistourneDTO> findOne(Long id) {
        log.debug("Request to get Ristourne : {}", id);
        return ristourneRepository.findById(id)
            .map(ristourneMapper::toDto);
    }

    /**
     * Delete the ristourne by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ristourne : {}", id);
        ristourneRepository.deleteById(id);
    }
}
