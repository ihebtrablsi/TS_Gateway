package com.africom.finance.service.mapper;


import com.africom.finance.domain.*;
import com.africom.finance.service.dto.IncentiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Incentive} and its DTO {@link IncentiveDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IncentiveMapper extends EntityMapper<IncentiveDTO, Incentive> {



    default Incentive fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incentive incentive = new Incentive();
        incentive.setId(id);
        return incentive;
    }
}
