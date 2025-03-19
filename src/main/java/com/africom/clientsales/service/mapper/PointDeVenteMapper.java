package com.africom.clientsales.service.mapper;


import com.africom.clientsales.domain.*;
import com.africom.clientsales.service.dto.PointDeVenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PointDeVente} and its DTO {@link PointDeVenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PointDeVenteMapper extends EntityMapper<PointDeVenteDTO, PointDeVente> {



    default PointDeVente fromId(Long id) {
        if (id == null) {
            return null;
        }
        PointDeVente pointDeVente = new PointDeVente();
        pointDeVente.setId(id);
        return pointDeVente;
    }
}
