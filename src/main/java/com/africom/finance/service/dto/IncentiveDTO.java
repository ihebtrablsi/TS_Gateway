package com.africom.finance.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.finance.domain.Incentive} entity.
 */
public class IncentiveDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nom;

    private String description;

    @NotNull
    private Instant dateDebut;

    @NotNull
    private Instant dateFin;

    @NotNull
    private String regles;

    @NotNull
    private String bonus;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getRegles() {
        return regles;
    }

    public void setRegles(String regles) {
        this.regles = regles;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncentiveDTO)) {
            return false;
        }

        return id != null && id.equals(((IncentiveDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncentiveDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", regles='" + getRegles() + "'" +
            ", bonus='" + getBonus() + "'" +
            "}";
    }
}
