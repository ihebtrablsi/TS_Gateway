package com.africom.productinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A AuditStock.
 */
@Entity
@Table(name = "audit_stock")
public class AuditStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_audit", nullable = false)
    private Instant dateAudit;

    @NotNull
    //@Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "commentaire")
    private String commentaire;

    @OneToMany(mappedBy = "audit")
    private Set<EcartStock> ecarts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "auditStocks", allowSetters = true)
    private Depot depot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateAudit() {
        return dateAudit;
    }

    public AuditStock dateAudit(Instant dateAudit) {
        this.dateAudit = dateAudit;
        return this;
    }

    public void setDateAudit(Instant dateAudit) {
        this.dateAudit = dateAudit;
    }

    public String getStatut() {
        return statut;
    }

    public AuditStock statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public AuditStock commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Set<EcartStock> getEcarts() {
        return ecarts;
    }

    public AuditStock ecarts(Set<EcartStock> ecartStocks) {
        this.ecarts = ecartStocks;
        return this;
    }

    public AuditStock addEcart(EcartStock ecartStock) {
        this.ecarts.add(ecartStock);
        ecartStock.setAudit(this);
        return this;
    }

    public AuditStock removeEcart(EcartStock ecartStock) {
        this.ecarts.remove(ecartStock);
        ecartStock.setAudit(null);
        return this;
    }

    public void setEcarts(Set<EcartStock> ecartStocks) {
        this.ecarts = ecartStocks;
    }

    public Depot getDepot() {
        return depot;
    }

    public AuditStock depot(Depot depot) {
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
        if (!(o instanceof AuditStock)) {
            return false;
        }
        return id != null && id.equals(((AuditStock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditStock{" +
            "id=" + getId() +
            ", dateAudit='" + getDateAudit() + "'" +
            ", statut='" + getStatut() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
