package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.ProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring", uses = {FamilleProduitMapper.class})
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {

    @Mapping(source = "famille.id", target = "familleId")
    ProduitDTO toDto(Produit produit);

    @Mapping(target = "codes", ignore = true)
    @Mapping(target = "removeCode", ignore = true)
    @Mapping(source = "familleId", target = "famille")
    Produit toEntity(ProduitDTO produitDTO);

    default Produit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }
}
