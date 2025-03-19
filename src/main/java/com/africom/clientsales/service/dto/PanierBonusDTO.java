package com.africom.clientsales.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.clientsales.domain.PanierBonus} entity.
 */
public class PanierBonusDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer totalPoints;

    @NotNull
    @DecimalMin(value = "0")
    private Double totalValeur;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getTotalValeur() {
        return totalValeur;
    }

    public void setTotalValeur(Double totalValeur) {
        this.totalValeur = totalValeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PanierBonusDTO)) {
            return false;
        }

        return id != null && id.equals(((PanierBonusDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PanierBonusDTO{" +
            "id=" + getId() +
            ", totalPoints=" + getTotalPoints() +
            ", totalValeur=" + getTotalValeur() +
            "}";
    }
}
