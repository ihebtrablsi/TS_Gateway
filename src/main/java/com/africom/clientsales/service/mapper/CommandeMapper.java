package com.africom.clientsales.service.mapper;


import com.africom.clientsales.domain.*;
import com.africom.clientsales.service.dto.CommandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, PointDeVenteMapper.class})
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "pointDeVente.id", target = "pointDeVenteId")
    CommandeDTO toDto(Commande commande);

    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "pointDeVenteId", target = "pointDeVente")
    Commande toEntity(CommandeDTO commandeDTO);

    default Commande fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commande commande = new Commande();
        commande.setId(id);
        return commande;
    }
}
