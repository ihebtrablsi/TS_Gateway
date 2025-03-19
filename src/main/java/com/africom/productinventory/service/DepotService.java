package com.africom.productinventory.service;

import com.africom.productinventory.domain.Depot;
import com.africom.productinventory.repository.DepotRepository;
import com.africom.productinventory.service.dto.DepotDTO;
import com.africom.productinventory.service.mapper.DepotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Depot}.
 */
@Service
@Transactional
public class DepotService {

    private final Logger log = LoggerFactory.getLogger(DepotService.class);

    private final DepotRepository depotRepository;

    private final DepotMapper depotMapper;

    public DepotService(DepotRepository depotRepository, DepotMapper depotMapper) {
        this.depotRepository = depotRepository;
        this.depotMapper = depotMapper;
    }

    /**
     * Save a depot.
     *
     * @param depotDTO the entity to save.
     * @return the persisted entity.
     */
    public DepotDTO save(DepotDTO depotDTO) {
        log.debug("Request to save Depot : {}", depotDTO);
        Depot depot = depotMapper.toEntity(depotDTO);
        depot = depotRepository.save(depot);
        return depotMapper.toDto(depot);
    }

    /**
     * Get all the depots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DepotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Depots");
        return depotRepository.findAll(pageable)
            .map(depotMapper::toDto);
    }


    /**
     * Get one depot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepotDTO> findOne(Long id) {
        log.debug("Request to get Depot : {}", id);
        return depotRepository.findById(id)
            .map(depotMapper::toDto);
    }

    /**
     * Delete the depot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Depot : {}", id);
        depotRepository.deleteById(id);
    }
}
