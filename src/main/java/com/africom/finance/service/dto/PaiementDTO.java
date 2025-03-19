package com.africom.finance.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.finance.domain.Paiement} entity.
 */
public class PaiementDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String reference;

    @NotNull
    private Instant datePaiement;

    @NotNull
    @DecimalMin(value = "0")
    private Double montant;

    @NotNull
    private String modePaiement;

    @NotNull
    private String statut;


    private Long factureId;

    private Long compteId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaiementDTO)) {
            return false;
        }

        return id != null && id.equals(((PaiementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", montant=" + getMontant() +
            ", modePaiement='" + getModePaiement() + "'" +
            ", statut='" + getStatut() + "'" +
            ", factureId=" + getFactureId() +
            ", compteId=" + getCompteId() +
            "}";
    }
}
