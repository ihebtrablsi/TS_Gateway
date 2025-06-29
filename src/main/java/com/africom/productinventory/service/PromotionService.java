package com.africom.productinventory.service;

import com.africom.productinventory.domain.Promotion;
import com.africom.productinventory.repository.MouvementStockRepository;
import com.africom.productinventory.repository.PromotionRepository;
import com.africom.productinventory.service.dto.PromotionDTO;
import com.africom.productinventory.service.mapper.PromotionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Promotion}.
 */
@Service
@Transactional
public class PromotionService {

    private final Logger log = LoggerFactory.getLogger(PromotionService.class);
    @Autowired
    private MouvementStockRepository mouvements;

    private final PromotionRepository promotionRepository;

    private final PromotionMapper promotionMapper;

    public PromotionService(PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    /**
     * Save a promotion.
     *
     * @param promotionDTO the entity to save.
     * @return the persisted entity.
     */
    public PromotionDTO save(PromotionDTO promotionDTO) {
        log.debug("Request to save Promotion : {}", promotionDTO);
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toDto(promotion);
    }

    /**
     * Get all the promotions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PromotionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Promotions");
        return promotionRepository.findAll(pageable)
            .map(promotionMapper::toDto);
    }


    /**
     * Get one promotion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PromotionDTO> findOne(Long id) {
        log.debug("Request to get Promotion : {}", id);
        return promotionRepository.findById(id)
            .map(promotionMapper::toDto);
    }

    /**
     * Delete the promotion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Promotion : {}", id);
        promotionRepository.deleteById(id);
    }
    public long getStock(Long produitId) {
        return mouvements.sumQuantiteByProduit(produitId).orElse(0L);
    }

    public PromotionDTO suggestPromotion(Long produitId) {
        long stock = getStock(produitId);
        double remiseRate = stock > 100 ? 10.0 : (stock > 50 ? 5.0 : 0.0);
        PromotionDTO dto = new PromotionDTO();
        dto.setRemise(remiseRate);
        dto.setNom("Promo auto → " + remiseRate + "%");
        return dto;
    }
}
