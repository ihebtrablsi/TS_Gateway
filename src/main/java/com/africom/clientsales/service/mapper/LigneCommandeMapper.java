package com.africom.clientsales.service.mapper;


import com.africom.clientsales.domain.*;
import com.africom.clientsales.service.dto.LigneCommandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LigneCommande} and its DTO {@link LigneCommandeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommandeMapper.class})
public interface LigneCommandeMapper extends EntityMapper<LigneCommandeDTO, LigneCommande> {

    @Mapping(source = "commande.id", target = "commandeId")
    LigneCommandeDTO toDto(LigneCommande ligneCommande);

    @Mapping(source = "commandeId", target = "commande")
    LigneCommande toEntity(LigneCommandeDTO ligneCommandeDTO);

    default LigneCommande fromId(Long id) {
        if (id == null) {
            return null;
        }
        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setId(id);
        return ligneCommande;
    }
}
