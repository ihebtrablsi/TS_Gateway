package com.africom.finance.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Ristourne.
 */
@Entity
@Table(name = "ristourne")
public class Ristourne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Column(name = "date_attribution", nullable = false)
    private Instant dateAttribution;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public Ristourne montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Instant getDateAttribution() {
        return dateAttribution;
    }

    public Ristourne dateAttribution(Instant dateAttribution) {
        this.dateAttribution = dateAttribution;
        return this;
    }

    public void setDateAttribution(Instant dateAttribution) {
        this.dateAttribution = dateAttribution;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ristourne)) {
            return false;
        }
        return id != null && id.equals(((Ristourne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ristourne{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", dateAttribution='" + getDateAttribution() + "'" +
            "}";
    }
}
