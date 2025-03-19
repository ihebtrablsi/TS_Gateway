package com.africom.finance.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.finance.domain.Lettrage} entity.
 */
public class LettrageDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant dateLettrage;

    @NotNull
    @DecimalMin(value = "0")
    private Double montantLettre;


    private Long factureId;

    private Long paiementId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateLettrage() {
        return dateLettrage;
    }

    public void setDateLettrage(Instant dateLettrage) {
        this.dateLettrage = dateLettrage;
    }

    public Double getMontantLettre() {
        return montantLettre;
    }

    public void setMontantLettre(Double montantLettre) {
        this.montantLettre = montantLettre;
    }

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    public Long getPaiementId() {
        return paiementId;
    }

    public void setPaiementId(Long paiementId) {
        this.paiementId = paiementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LettrageDTO)) {
            return false;
        }

        return id != null && id.equals(((LettrageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LettrageDTO{" +
            "id=" + getId() +
            ", dateLettrage='" + getDateLettrage() + "'" +
            ", montantLettre=" + getMontantLettre() +
            ", factureId=" + getFactureId() +
            ", paiementId=" + getPaiementId() +
            "}";
    }
}
