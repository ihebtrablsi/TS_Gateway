package com.africom.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Lettrage.
 */
@Entity
@Table(name = "lettrage")
public class Lettrage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_lettrage", nullable = false)
    private Instant dateLettrage;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "montant_lettre", nullable = false)
    private Double montantLettre;

    @ManyToOne
    @JsonIgnoreProperties(value = "lettrages", allowSetters = true)
    private Facture facture;

    @ManyToOne
    @JsonIgnoreProperties(value = "lettrages", allowSetters = true)
    private Paiement paiement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateLettrage() {
        return dateLettrage;
    }

    public Lettrage dateLettrage(Instant dateLettrage) {
        this.dateLettrage = dateLettrage;
        return this;
    }

    public void setDateLettrage(Instant dateLettrage) {
        this.dateLettrage = dateLettrage;
    }

    public Double getMontantLettre() {
        return montantLettre;
    }

    public Lettrage montantLettre(Double montantLettre) {
        this.montantLettre = montantLettre;
        return this;
    }

    public void setMontantLettre(Double montantLettre) {
        this.montantLettre = montantLettre;
    }

    public Facture getFacture() {
        return facture;
    }

    public Lettrage facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public Lettrage paiement(Paiement paiement) {
        this.paiement = paiement;
        return this;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lettrage)) {
            return false;
        }
        return id != null && id.equals(((Lettrage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lettrage{" +
            "id=" + getId() +
            ", dateLettrage='" + getDateLettrage() + "'" +
            ", montantLettre=" + getMontantLettre() +
            "}";
    }
}
