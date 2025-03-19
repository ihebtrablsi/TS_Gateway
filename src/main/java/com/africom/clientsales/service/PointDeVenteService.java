package com.africom.clientsales.service;

import com.africom.clientsales.domain.PointDeVente;
import com.africom.clientsales.repository.PointDeVenteRepository;
import com.africom.clientsales.service.dto.PointDeVenteDTO;
import com.africom.clientsales.service.mapper.PointDeVenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PointDeVente}.
 */
@Service
@Transactional
public class PointDeVenteService {

    private final Logger log = LoggerFactory.getLogger(PointDeVenteService.class);

    private final PointDeVenteRepository pointDeVenteRepository;

    private final PointDeVenteMapper pointDeVenteMapper;

    public PointDeVenteService(PointDeVenteRepository pointDeVenteRepository, PointDeVenteMapper pointDeVenteMapper) {
        this.pointDeVenteRepository = pointDeVenteRepository;
        this.pointDeVenteMapper = pointDeVenteMapper;
    }

    /**
     * Save a pointDeVente.
     *
     * @param pointDeVenteDTO the entity to save.
     * @return the persisted entity.
     */
    public PointDeVenteDTO save(PointDeVenteDTO pointDeVenteDTO) {
        log.debug("Request to save PointDeVente : {}", pointDeVenteDTO);
        PointDeVente pointDeVente = pointDeVenteMapper.toEntity(pointDeVenteDTO);
        pointDeVente = pointDeVenteRepository.save(pointDeVente);
        return pointDeVenteMapper.toDto(pointDeVente);
    }

    /**
     * Get all the pointDeVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PointDeVenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PointDeVentes");
        return pointDeVenteRepository.findAll(pageable)
            .map(pointDeVenteMapper::toDto);
    }


    /**
     * Get one pointDeVente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PointDeVenteDTO> findOne(Long id) {
        log.debug("Request to get PointDeVente : {}", id);
        return pointDeVenteRepository.findById(id)
            .map(pointDeVenteMapper::toDto);
    }

    /**
     * Delete the pointDeVente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PointDeVente : {}", id);
        pointDeVenteRepository.deleteById(id);
    }
}
