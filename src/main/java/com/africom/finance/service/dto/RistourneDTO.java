package com.africom.finance.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.finance.domain.Ristourne} entity.
 */
public class RistourneDTO implements Serializable {
    
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private Double montant;

    @NotNull
    private Instant dateAttribution;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RistourneDTO)) {
            return false;
        }

        return id != null && id.equals(((RistourneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RistourneDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", dateAttribution='" + getDateAttribution() + "'" +
            "}";
    }
}
