package com.africom.clientsales.service.mapper;


import com.africom.clientsales.domain.*;
import com.africom.clientsales.service.dto.PanierBonusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PanierBonus} and its DTO {@link PanierBonusDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PanierBonusMapper extends EntityMapper<PanierBonusDTO, PanierBonus> {



    default PanierBonus fromId(Long id) {
        if (id == null) {
            return null;
        }
        PanierBonus panierBonus = new PanierBonus();
        panierBonus.setId(id);
        return panierBonus;
    }
}
