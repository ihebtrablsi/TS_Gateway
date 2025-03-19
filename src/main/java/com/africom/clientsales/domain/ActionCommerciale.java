package com.africom.clientsales.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ActionCommerciale.
 */
@Entity
@Table(name = "action_commerciale")
public class ActionCommerciale implements Serializable {

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
    @Column(name = "type_action", nullable = false)
    private String typeAction;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @ManyToOne
    @JsonIgnoreProperties(value = "actionCommerciales", allowSetters = true)
    private PointDeVente pointDeVente;

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

    public ActionCommerciale nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public ActionCommerciale description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public ActionCommerciale dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public ActionCommerciale dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public ActionCommerciale typeAction(String typeAction) {
        this.typeAction = typeAction;
        return this;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public String getStatut() {
        return statut;
    }

    public ActionCommerciale statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public PointDeVente getPointDeVente() {
        return pointDeVente;
    }

    public ActionCommerciale pointDeVente(PointDeVente pointDeVente) {
        this.pointDeVente = pointDeVente;
        return this;
    }

    public void setPointDeVente(PointDeVente pointDeVente) {
        this.pointDeVente = pointDeVente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionCommerciale)) {
            return false;
        }
        return id != null && id.equals(((ActionCommerciale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionCommerciale{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", typeAction='" + getTypeAction() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
