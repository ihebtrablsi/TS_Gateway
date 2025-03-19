package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.PromotionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Promotion} and its DTO {@link PromotionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface PromotionMapper extends EntityMapper<PromotionDTO, Promotion> {

    @Mapping(source = "produit.id", target = "produitId")
    PromotionDTO toDto(Promotion promotion);

    @Mapping(source = "produitId", target = "produit")
    Promotion toEntity(PromotionDTO promotionDTO);

    default Promotion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Promotion promotion = new Promotion();
        promotion.setId(id);
        return promotion;
    }
}
