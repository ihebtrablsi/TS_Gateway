package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.AuditStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditStock} and its DTO {@link AuditStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepotMapper.class})
public interface AuditStockMapper extends EntityMapper<AuditStockDTO, AuditStock> {

    @Mapping(source = "depot.id", target = "depotId")
    AuditStockDTO toDto(AuditStock auditStock);

    @Mapping(target = "ecarts", ignore = true)
    @Mapping(target = "removeEcart", ignore = true)
    @Mapping(source = "depotId", target = "depot")
    AuditStock toEntity(AuditStockDTO auditStockDTO);

    default AuditStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuditStock auditStock = new AuditStock();
        auditStock.setId(id);
        return auditStock;
    }
}
