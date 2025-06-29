package com.africom.productinventory.service.dto;

import com.africom.productinventory.domain.enumeration.TypeDepot;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.productinventory.domain.Depot} entity.
 */
public class DepotDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String adresse;

    @NotNull
    private String typeDepot;

    @NotNull
    @Min(value = 0)
    private Integer capacite;


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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTypeDepot() {
        return typeDepot;
    }

    public void setTypeDepot(String typeDepot) {
        this.typeDepot = typeDepot;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepotDTO)) {
            return false;
        }

        return id != null && id.equals(((DepotDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepotDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", typeDepot='" + getTypeDepot() + "'" +
            ", capacite=" + getCapacite() +
            "}";
    }
}
