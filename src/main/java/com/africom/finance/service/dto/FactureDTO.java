package com.africom.finance.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.finance.domain.Facture} entity.
 */
public class FactureDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numeroFacture;

    @NotNull
    private Instant dateFacture;

    @NotNull
    @DecimalMin(value = "0")
    private Double montantTotal;

    @NotNull
    private String statut;

    private Instant dateEcheance;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public Instant getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Instant dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Instant getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Instant dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureDTO)) {
            return false;
        }

        return id != null && id.equals(((FactureDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureDTO{" +
            "id=" + getId() +
            ", numeroFacture='" + getNumeroFacture() + "'" +
            ", dateFacture='" + getDateFacture() + "'" +
            ", montantTotal=" + getMontantTotal() +
            ", statut='" + getStatut() + "'" +
            ", dateEcheance='" + getDateEcheance() + "'" +
            "}";
    }
}
