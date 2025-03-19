package com.africom.finance.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.finance.domain.Bonus} entity.
 */
public class BonusDTO implements Serializable {
    
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private Double montant;

    @NotNull
    private Instant dateAttribution;

    @NotNull
    private String statut;


    private Long incentiveId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Instant getDateAttribution() {
        return dateAttribution;
    }

    public void setDateAttribution(Instant dateAttribution) {
        this.dateAttribution = dateAttribution;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getIncentiveId() {
        return incentiveId;
    }

    public void setIncentiveId(Long incentiveId) {
        this.incentiveId = incentiveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonusDTO)) {
            return false;
        }

        return id != null && id.equals(((BonusDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonusDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", dateAttribution='" + getDateAttribution() + "'" +
            ", statut='" + getStatut() + "'" +
            ", incentiveId=" + getIncentiveId() +
            "}";
    }
}
