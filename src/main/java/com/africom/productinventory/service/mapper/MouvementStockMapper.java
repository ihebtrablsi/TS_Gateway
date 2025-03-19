package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.MouvementStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MouvementStock} and its DTO {@link MouvementStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class, DepotMapper.class})
public interface MouvementStockMapper extends EntityMapper<MouvementStockDTO, MouvementStock> {

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "depot.id", target = "depotId")
    MouvementStockDTO toDto(MouvementStock mouvementStock);

    @Mapping(source = "produitId", target = "produit")
    @Mapping(source = "depotId", target = "depot")
    MouvementStock toEntity(MouvementStockDTO mouvementStockDTO);

    default MouvementStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        MouvementStock mouvementStock = new MouvementStock();
        mouvementStock.setId(id);
        return mouvementStock;
    }
}
