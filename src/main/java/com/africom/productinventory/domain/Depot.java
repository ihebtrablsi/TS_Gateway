package com.africom.productinventory.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Depot.
 */
@Entity
@Table(name = "depot")
public class Depot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    //@Enumerated(EnumType.STRING)
    @Column(name = "type_depot", nullable = false)
    private String typeDepot;

    @NotNull
    @Min(value = 0)
    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Depot nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Depot adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTypeDepot() {
        return typeDepot;
    }

    public Depot typeDepot(String typeDepot) {
        this.typeDepot = typeDepot;
        return this;
    }

    public void setTypeDepot(String typeDepot) {
        this.typeDepot = typeDepot;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public Depot capacite(Integer capacite) {
        this.capacite = capacite;
        return this;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depot)) {
            return false;
        }
        return id != null && id.equals(((Depot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Depot{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", typeDepot='" + getTypeDepot() + "'" +
            ", capacite=" + getCapacite() +
            "}";
    }
}
