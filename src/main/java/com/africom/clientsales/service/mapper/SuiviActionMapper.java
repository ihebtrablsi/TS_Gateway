package com.africom.clientsales.service.mapper;


import com.africom.clientsales.domain.*;
import com.africom.clientsales.service.dto.SuiviActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SuiviAction} and its DTO {@link SuiviActionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActionCommercialeMapper.class})
public interface SuiviActionMapper extends EntityMapper<SuiviActionDTO, SuiviAction> {

    @Mapping(source = "actionCommerciale.id", target = "actionCommercialeId")
    SuiviActionDTO toDto(SuiviAction suiviAction);

    @Mapping(source = "actionCommercialeId", target = "actionCommerciale")
    SuiviAction toEntity(SuiviActionDTO suiviActionDTO);

    default SuiviAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        SuiviAction suiviAction = new SuiviAction();
        suiviAction.setId(id);
        return suiviAction;
    }
}
