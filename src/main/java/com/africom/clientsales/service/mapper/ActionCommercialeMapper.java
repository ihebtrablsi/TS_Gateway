package com.africom.clientsales.service.mapper;


import com.africom.clientsales.domain.*;
import com.africom.clientsales.service.dto.ActionCommercialeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActionCommerciale} and its DTO {@link ActionCommercialeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PointDeVenteMapper.class})
public interface ActionCommercialeMapper extends EntityMapper<ActionCommercialeDTO, ActionCommerciale> {

    @Mapping(source = "pointDeVente.id", target = "pointDeVenteId")
    ActionCommercialeDTO toDto(ActionCommerciale actionCommerciale);

    @Mapping(source = "pointDeVenteId", target = "pointDeVente")
    ActionCommerciale toEntity(ActionCommercialeDTO actionCommercialeDTO);

    default ActionCommerciale fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActionCommerciale actionCommerciale = new ActionCommerciale();
        actionCommerciale.setId(id);
        return actionCommerciale;
    }
}
