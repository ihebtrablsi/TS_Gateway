package com.africom.productinventory.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.productinventory.domain.MouvementStock} entity.
 */
public class MouvementStockDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String typeMouvement;

    @NotNull
    @Min(value = 0)
    private Integer quantite;

    @NotNull
    private Instant dateMouvement;


    private Long produitId;

    private Long depotId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Instant getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(Instant dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Long getDepotId() {
        return depotId;
    }

    public void setDepotId(Long depotId) {
        this.depotId = depotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MouvementStockDTO)) {
            return false;
        }

        return id != null && id.equals(((MouvementStockDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementStockDTO{" +
            "id=" + getId() +
            ", typeMouvement='" + getTypeMouvement() + "'" +
            ", quantite=" + getQuantite() +
            ", dateMouvement='" + getDateMouvement() + "'" +
            ", produitId=" + getProduitId() +
            ", depotId=" + getDepotId() +
            "}";
    }
}
