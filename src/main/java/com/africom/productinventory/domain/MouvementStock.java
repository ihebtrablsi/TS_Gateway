package com.africom.productinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A MouvementStock.
 */
@Entity
@Table(name = "mouvement_stock")
public class MouvementStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "type_mouvement", nullable = false)
    private String typeMouvement;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @NotNull
    @Column(name = "date_mouvement", nullable = false)
    private Instant dateMouvement;

    @ManyToOne
    @JsonIgnoreProperties(value = "mouvementStocks", allowSetters = true)
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = "mouvementStocks", allowSetters = true)
    private Depot depot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public MouvementStock typeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
        return this;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public MouvementStock quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Instant getDateMouvement() {
        return dateMouvement;
    }

    public MouvementStock dateMouvement(Instant dateMouvement) {
        this.dateMouvement = dateMouvement;
        return this;
    }

    public void setDateMouvement(Instant dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public Produit getProduit() {
        return produit;
    }

    public MouvementStock produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Depot getDepot() {
        return depot;
    }

    public MouvementStock depot(Depot depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MouvementStock)) {
            return false;
        }
        return id != null && id.equals(((MouvementStock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementStock{" +
            "id=" + getId() +
            ", typeMouvement='" + getTypeMouvement() + "'" +
            ", quantite=" + getQuantite() +
            ", dateMouvement='" + getDateMouvement() + "'" +
            "}";
    }
}
