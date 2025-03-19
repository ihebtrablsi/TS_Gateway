package com.africom.productinventory.service;

import com.africom.productinventory.domain.AuditStock;
import com.africom.productinventory.repository.AuditStockRepository;
import com.africom.productinventory.service.dto.AuditStockDTO;
import com.africom.productinventory.service.mapper.AuditStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AuditStock}.
 */
@Service
@Transactional
public class AuditStockService {

    private final Logger log = LoggerFactory.getLogger(AuditStockService.class);

    private final AuditStockRepository auditStockRepository;

    private final AuditStockMapper auditStockMapper;

    public AuditStockService(AuditStockRepository auditStockRepository, AuditStockMapper auditStockMapper) {
        this.auditStockRepository = auditStockRepository;
        this.auditStockMapper = auditStockMapper;
    }

    /**
     * Save a auditStock.
     *
     * @param auditStockDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditStockDTO save(AuditStockDTO auditStockDTO) {
        log.debug("Request to save AuditStock : {}", auditStockDTO);
        AuditStock auditStock = auditStockMapper.toEntity(auditStockDTO);
        auditStock = auditStockRepository.save(auditStock);
        return auditStockMapper.toDto(auditStock);
    }

    /**
     * Get all the auditStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditStockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditStocks");
        return auditStockRepository.findAll(pageable)
            .map(auditStockMapper::toDto);
    }


    /**
     * Get one auditStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditStockDTO> findOne(Long id) {
        log.debug("Request to get AuditStock : {}", id);
        return auditStockRepository.findById(id)
            .map(auditStockMapper::toDto);
    }

    /**
     * Delete the auditStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditStock : {}", id);
        auditStockRepository.deleteById(id);
    }
}
