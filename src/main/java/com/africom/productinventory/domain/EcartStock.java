package com.africom.productinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EcartStock.
 */
@Entity
@Table(name = "ecart_stock")
public class EcartStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "quantite_theorique", nullable = false)
    private Integer quantiteTheorique;

    @NotNull
    @Column(name = "quantite_physique", nullable = false)
    private Integer quantitePhysique;

    @NotNull
    @Column(name = "ecart", nullable = false)
    private Integer ecart;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties(value = "ecartStocks", allowSetters = true)
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = "ecarts", allowSetters = true)
    private AuditStock audit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantiteTheorique() {
        return quantiteTheorique;
    }

    public EcartStock quantiteTheorique(Integer quantiteTheorique) {
        this.quantiteTheorique = quantiteTheorique;
        return this;
    }

    public void setQuantiteTheorique(Integer quantiteTheorique) {
        this.quantiteTheorique = quantiteTheorique;
    }

    public Integer getQuantitePhysique() {
        return quantitePhysique;
    }

    public EcartStock quantitePhysique(Integer quantitePhysique) {
        this.quantitePhysique = quantitePhysique;
        return this;
    }

    public void setQuantitePhysique(Integer quantitePhysique) {
        this.quantitePhysique = quantitePhysique;
    }

    public Integer getEcart() {
        return ecart;
    }

    public EcartStock ecart(Integer ecart) {
        this.ecart = ecart;
        return this;
    }

    public void setEcart(Integer ecart) {
        this.ecart = ecart;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public EcartStock commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Produit getProduit() {
        return produit;
    }

    public EcartStock produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public AuditStock getAudit() {
        return audit;
    }

    public EcartStock audit(AuditStock auditStock) {
        this.audit = auditStock;
        return this;
    }

    public void setAudit(AuditStock auditStock) {
        this.audit = auditStock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EcartStock)) {
            return false;
        }
        return id != null && id.equals(((EcartStock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcartStock{" +
            "id=" + getId() +
            ", quantiteTheorique=" + getQuantiteTheorique() +
            ", quantitePhysique=" + getQuantitePhysique() +
            ", ecart=" + getEcart() +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
