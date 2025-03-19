package com.africom.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @NotNull
    @Column(name = "date_paiement", nullable = false)
    private Instant datePaiement;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Column(name = "mode_paiement", nullable = false)
    private String modePaiement;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @ManyToOne
    @JsonIgnoreProperties(value = "paiements", allowSetters = true)
    private Facture facture;

    @ManyToOne
    @JsonIgnoreProperties(value = "paiements", allowSetters = true)
    private Compte compte;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Paiement reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public Paiement datePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
        return this;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public Paiement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public Paiement modePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
        return this;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getStatut() {
        return statut;
    }

    public Paiement statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Facture getFacture() {
        return facture;
    }

    public Paiement facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Compte getCompte() {
        return compte;
    }

    public Paiement compte(Compte compte) {
        this.compte = compte;
        return this;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return id != null && id.equals(((Paiement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", montant=" + getMontant() +
            ", modePaiement='" + getModePaiement() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
