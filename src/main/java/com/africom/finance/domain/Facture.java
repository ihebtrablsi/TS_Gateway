package com.africom.finance.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_facture", nullable = false, unique = true)
    private String numeroFacture;

    @NotNull
    @Column(name = "date_facture", nullable = false)
    private Instant dateFacture;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "montant_total", nullable = false)
    private Double montantTotal;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "date_echeance")
    private Instant dateEcheance;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public Facture numeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
        return this;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public Instant getDateFacture() {
        return dateFacture;
    }

    public Facture dateFacture(Instant dateFacture) {
        this.dateFacture = dateFacture;
        return this;
    }

    public void setDateFacture(Instant dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public Facture montantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
        return this;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return statut;
    }

    public Facture statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Instant getDateEcheance() {
        return dateEcheance;
    }

    public Facture dateEcheance(Instant dateEcheance) {
        this.dateEcheance = dateEcheance;
        return this;
    }

    public void setDateEcheance(Instant dateEcheance) {
        this.dateEcheance = dateEcheance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", numeroFacture='" + getNumeroFacture() + "'" +
            ", dateFacture='" + getDateFacture() + "'" +
            ", montantTotal=" + getMontantTotal() +
            ", statut='" + getStatut() + "'" +
            ", dateEcheance='" + getDateEcheance() + "'" +
            "}";
    }
}
