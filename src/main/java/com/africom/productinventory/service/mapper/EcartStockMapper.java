package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.EcartStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EcartStock} and its DTO {@link EcartStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class, AuditStockMapper.class})
public interface EcartStockMapper extends EntityMapper<EcartStockDTO, EcartStock> {

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "audit.id", target = "auditId")
    EcartStockDTO toDto(EcartStock ecartStock);

    @Mapping(source = "produitId", target = "produit")
    @Mapping(source = "auditId", target = "audit")
    EcartStock toEntity(EcartStockDTO ecartStockDTO);

    default EcartStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        EcartStock ecartStock = new EcartStock();
        ecartStock.setId(id);
        return ecartStock;
    }
}
