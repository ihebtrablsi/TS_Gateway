package com.africom.finance.service.mapper;


import com.africom.finance.domain.*;
import com.africom.finance.service.dto.RistourneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ristourne} and its DTO {@link RistourneDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RistourneMapper extends EntityMapper<RistourneDTO, Ristourne> {



    default Ristourne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ristourne ristourne = new Ristourne();
        ristourne.setId(id);
        return ristourne;
    }
}
