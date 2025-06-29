package com.africom.productinventory.service;

import com.africom.productinventory.domain.MouvementStock;
import com.africom.productinventory.repository.MouvementStockRepository;
import com.africom.productinventory.service.dto.MouvementStockDTO;
import com.africom.productinventory.service.dto.ProduitDTO;
import com.africom.productinventory.service.mapper.MouvementStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MouvementStock}.
 */
@Service
@Transactional
public class MouvementStockService {

    private final Logger log = LoggerFactory.getLogger(MouvementStockService.class);

    private final MouvementStockRepository mouvementStockRepository;

    private final MouvementStockMapper mouvementStockMapper;

    public MouvementStockService(MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    /**
     * Save a mouvementStock.
     *
     * @param mouvementStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementStockDTO save(MouvementStockDTO mouvementStockDTO) {
        log.debug("Request to save MouvementStock : {}", mouvementStockDTO);
        MouvementStock mouvementStock = mouvementStockMapper.toEntity(mouvementStockDTO);
        mouvementStock = mouvementStockRepository.save(mouvementStock);
        return mouvementStockMapper.toDto(mouvementStock);
    }

    /**
     * Get all the mouvementStocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MouvementStockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MouvementStocks");
        return mouvementStockRepository.findAll(pageable)
            .map(mouvementStockMapper::toDto);
    }


    /**
     * Get one mouvementStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MouvementStockDTO> findOne(Long id) {
        log.debug("Request to get MouvementStock : {}", id);
        return mouvementStockRepository.findById(id)
            .map(mouvementStockMapper::toDto);
    }

    /**
     * Delete the mouvementStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MouvementStock : {}", id);
        mouvementStockRepository.deleteById(id);
    }
    public List<ProduitDTO> detecterProduitsEnRupture(List<ProduitDTO> produits) {
        return produits.stream()
            .filter(p -> p.getStock() < 5) // seuil IA ajustable
            .collect(Collectors.toList());
    }

    public String suggérerRéapprovisionnement(ProduitDTO produit) {
        String prompt = String.format("Le produit %s a un stock de %d. Suggère un niveau de commande optimal.", produit.getNom(), produit.getStock());
        return openAiCall(prompt);
    }

    private String openAiCall(String prompt) {
        return "Commander 100 unités"; // réponse simulée
    }

}
