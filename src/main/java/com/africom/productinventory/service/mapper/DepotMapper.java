package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.DepotDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Depot} and its DTO {@link DepotDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepotMapper extends EntityMapper<DepotDTO, Depot> {



    default Depot fromId(Long id) {
        if (id == null) {
            return null;
        }
        Depot depot = new Depot();
        depot.setId(id);
        return depot;
    }
}
