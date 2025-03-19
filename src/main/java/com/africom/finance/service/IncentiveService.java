package com.africom.finance.service;

import com.africom.finance.domain.Incentive;
import com.africom.finance.repository.IncentiveRepository;
import com.africom.finance.service.dto.IncentiveDTO;
import com.africom.finance.service.mapper.IncentiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Incentive}.
 */
@Service
@Transactional
public class IncentiveService {

    private final Logger log = LoggerFactory.getLogger(IncentiveService.class);

    private final IncentiveRepository incentiveRepository;

    private final IncentiveMapper incentiveMapper;

    public IncentiveService(IncentiveRepository incentiveRepository, IncentiveMapper incentiveMapper) {
        this.incentiveRepository = incentiveRepository;
        this.incentiveMapper = incentiveMapper;
    }

    /**
     * Save a incentive.
     *
     * @param incentiveDTO the entity to save.
     * @return the persisted entity.
     */
    public IncentiveDTO save(IncentiveDTO incentiveDTO) {
        log.debug("Request to save Incentive : {}", incentiveDTO);
        Incentive incentive = incentiveMapper.toEntity(incentiveDTO);
        incentive = incentiveRepository.save(incentive);
        return incentiveMapper.toDto(incentive);
    }

    /**
     * Get all the incentives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IncentiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Incentives");
        return incentiveRepository.findAll(pageable)
            .map(incentiveMapper::toDto);
    }


    /**
     * Get one incentive by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IncentiveDTO> findOne(Long id) {
        log.debug("Request to get Incentive : {}", id);
        return incentiveRepository.findById(id)
            .map(incentiveMapper::toDto);
    }

    /**
     * Delete the incentive by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Incentive : {}", id);
        incentiveRepository.deleteById(id);
    }
}
