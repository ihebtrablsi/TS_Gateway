package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.FamilleProduitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilleProduit} and its DTO {@link FamilleProduitDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FamilleProduitMapper extends EntityMapper<FamilleProduitDTO, FamilleProduit> {



    default FamilleProduit fromId(Long id) {
        if (id == null) {
            return null;
        }
        FamilleProduit familleProduit = new FamilleProduit();
        familleProduit.setId(id);
        return familleProduit;
    }
}
