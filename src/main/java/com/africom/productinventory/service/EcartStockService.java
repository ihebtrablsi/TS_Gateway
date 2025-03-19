package com.africom.productinventory.service;

import com.africom.productinventory.domain.EcartStock;
import com.africom.productinventory.repository.EcartStockRepository;
import com.africom.productinventory.service.dto.EcartStockDTO;
import com.africom.productinventory.service.mapper.EcartStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EcartStock}.
 */
@Service
@Transactional
public class EcartStockService {

    private final Logger log = LoggerFactory.getLogger(EcartStockService.class);

    private final EcartStockRepository ecartStockRepository;

    private final EcartStockMapper ecartStockMapper;

    public EcartStockService(EcartStockRepository ecartStockRepository, EcartStockMapper ecartStockMapper) {
        this.ecartStockRepository = ecartStockRepository;
        this.ecartStockMapper = ecartStockMapper;
    }

    /**
     * Save a ecartStock.
     *
     * @param ecartStockDTO the entity to save.
     * @return the persisted entity.
     */
    public EcartStockDTO save(EcartStockDTO ecartStockDTO) {
        log.debug("Request to save EcartStock : {}", ecartStockDTO);
        EcartStock ecartStock = ecartStockMapper.toEntity(ecartStockDTO);
        ecartStock = ecartStockRepository.save(ecartStock);
        return ecartStockMapper.toDto(ecartStock);
    }

    /**
     * Get all the ecartStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EcartStockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EcartStocks");
        return ecartStockRepository.findAll(pageable)
            .map(ecartStockMapper::toDto);
    }


    /**
     * Get one ecartStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EcartStockDTO> findOne(Long id) {
        log.debug("Request to get EcartStock : {}", id);
        return ecartStockRepository.findById(id)
            .map(ecartStockMapper::toDto);
    }

    /**
     * Delete the ecartStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EcartStock : {}", id);
        ecartStockRepository.deleteById(id);
    }
}
