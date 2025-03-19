package com.africom.productinventory.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.productinventory.domain.EcartStock} entity.
 */
public class EcartStockDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer quantiteTheorique;

    @NotNull
    private Integer quantitePhysique;

    @NotNull
    private Integer ecart;

    private String commentaire;


    private Long produitId;

    private Long auditId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantiteTheorique() {
        return quantiteTheorique;
    }

    public void setQuantiteTheorique(Integer quantiteTheorique) {
        this.quantiteTheorique = quantiteTheorique;
    }

    public Integer getQuantitePhysique() {
        return quantitePhysique;
    }

    public void setQuantitePhysique(Integer quantitePhysique) {
        this.quantitePhysique = quantitePhysique;
    }

    public Integer getEcart() {
        return ecart;
    }

    public void setEcart(Integer ecart) {
        this.ecart = ecart;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditStockId) {
        this.auditId = auditStockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcartStockDTO)) {
            return false;
        }

        return id != null && id.equals(((EcartStockDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcartStockDTO{" +
            "id=" + getId() +
            ", quantiteTheorique=" + getQuantiteTheorique() +
            ", quantitePhysique=" + getQuantitePhysique() +
            ", ecart=" + getEcart() +
            ", commentaire='" + getCommentaire() + "'" +
            ", produitId=" + getProduitId() +
            ", auditId=" + getAuditId() +
            "}";
    }
}
