package com.africom.finance.service.mapper;


import com.africom.finance.domain.*;
import com.africom.finance.service.dto.BonusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bonus} and its DTO {@link BonusDTO}.
 */
@Mapper(componentModel = "spring", uses = {IncentiveMapper.class})
public interface BonusMapper extends EntityMapper<BonusDTO, Bonus> {

    @Mapping(source = "incentive.id", target = "incentiveId")
    BonusDTO toDto(Bonus bonus);

    @Mapping(source = "incentiveId", target = "incentive")
    Bonus toEntity(BonusDTO bonusDTO);

    default Bonus fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bonus bonus = new Bonus();
        bonus.setId(id);
        return bonus;
    }
}
