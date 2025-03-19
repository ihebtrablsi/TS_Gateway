package com.africom.clientsales.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PanierBonus.
 */
@Entity
@Table(name = "panier_bonus")
public class PanierBonus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "total_points", nullable = false)
    private Integer totalPoints;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_valeur", nullable = false)
    private Double totalValeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public PanierBonus totalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getTotalValeur() {
        return totalValeur;
    }

    public PanierBonus totalValeur(Double totalValeur) {
        this.totalValeur = totalValeur;
        return this;
    }

    public void setTotalValeur(Double totalValeur) {
        this.totalValeur = totalValeur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PanierBonus)) {
            return false;
        }
        return id != null && id.equals(((PanierBonus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PanierBonus{" +
            "id=" + getId() +
            ", totalPoints=" + getTotalPoints() +
            ", totalValeur=" + getTotalValeur() +
            "}";
    }
}
