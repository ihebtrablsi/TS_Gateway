package com.africom.finance.service.mapper;


import com.africom.finance.domain.*;
import com.africom.finance.service.dto.PaiementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring", uses = {FactureMapper.class, CompteMapper.class})
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {

    @Mapping(source = "facture.id", target = "factureId")
    @Mapping(source = "compte.id", target = "compteId")
    PaiementDTO toDto(Paiement paiement);

    @Mapping(source = "factureId", target = "facture")
    @Mapping(source = "compteId", target = "compte")
    Paiement toEntity(PaiementDTO paiementDTO);

    default Paiement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paiement paiement = new Paiement();
        paiement.setId(id);
        return paiement;
    }
}
