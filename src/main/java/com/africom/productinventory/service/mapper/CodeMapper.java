package com.africom.productinventory.service.mapper;


import com.africom.productinventory.domain.*;
import com.africom.productinventory.service.dto.CodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Code} and its DTO {@link CodeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface CodeMapper extends EntityMapper<CodeDTO, Code> {

    @Mapping(source = "produit.id", target = "produitId")
    CodeDTO toDto(Code code);

    @Mapping(source = "produitId", target = "produit")
    Code toEntity(CodeDTO codeDTO);

    default Code fromId(Long id) {
        if (id == null) {
            return null;
        }
        Code code = new Code();
        code.setId(id);
        return code;
    }
}
