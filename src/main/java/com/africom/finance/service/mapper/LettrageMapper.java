package com.africom.finance.service.mapper;


import com.africom.finance.domain.*;
import com.africom.finance.service.dto.LettrageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lettrage} and its DTO {@link LettrageDTO}.
 */
@Mapper(componentModel = "spring", uses = {FactureMapper.class, PaiementMapper.class})
public interface LettrageMapper extends EntityMapper<LettrageDTO, Lettrage> {

    @Mapping(source = "facture.id", target = "factureId")
    @Mapping(source = "paiement.id", target = "paiementId")
    LettrageDTO toDto(Lettrage lettrage);

    @Mapping(source = "factureId", target = "facture")
    @Mapping(source = "paiementId", target = "paiement")
    Lettrage toEntity(LettrageDTO lettrageDTO);

    default Lettrage fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lettrage lettrage = new Lettrage();
        lettrage.setId(id);
        return lettrage;
    }
}
