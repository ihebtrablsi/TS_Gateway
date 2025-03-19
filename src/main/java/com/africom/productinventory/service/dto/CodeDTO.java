package com.africom.productinventory.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.productinventory.domain.Code} entity.
 */
public class CodeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String code;


    private Long produitId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodeDTO)) {
            return false;
        }

        return id != null && id.equals(((CodeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", produitId=" + getProduitId() +
            "}";
    }
}
