package com.africom.productinventory.service.dto;

import com.africom.productinventory.domain.enumeration.StatutAuditStock;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.productinventory.domain.AuditStock} entity.
 */
public class AuditStockDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dateAudit;

    @NotNull
    private String statut;

    private String commentaire;


    private Long depotId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateAudit() {
        return dateAudit;
    }

    public void setDateAudit(Instant dateAudit) {
        this.dateAudit = dateAudit;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
        if (!(o instanceof AuditStockDTO)) {
            return false;
        }

        return id != null && id.equals(((AuditStockDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditStockDTO{" +
            "id=" + getId() +
            ", dateAudit='" + getDateAudit() + "'" +
            ", statut='" + getStatut() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", depotId=" + getDepotId() +
            "}";
    }
}
