package com.africom.finance.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Incentive.
 */
@Entity
@Table(name = "incentive")
public class Incentive implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private Instant dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private Instant dateFin;

    @NotNull
    @Column(name = "regles", nullable = false)
    private String regles;

    @NotNull
    @Column(name = "bonus", nullable = false)
    private String bonus;

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

    public Incentive nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Incentive description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Incentive dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public Incentive dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getRegles() {
        return regles;
    }

    public Incentive regles(String regles) {
        this.regles = regles;
        return this;
    }

    public void setRegles(String regles) {
        this.regles = regles;
    }

    public String getBonus() {
        return bonus;
    }

    public Incentive bonus(String bonus) {
        this.bonus = bonus;
        return this;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incentive)) {
            return false;
        }
        return id != null && id.equals(((Incentive) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Incentive{" +
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
