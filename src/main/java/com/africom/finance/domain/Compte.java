package com.africom.finance.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_compte", nullable = false)
    private String numeroCompte;

    @NotNull
    @Column(name = "solde", nullable = false)
    private Double solde;

    @Column(name = "type_compte")
    private String typeCompte;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public Compte numeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
        return this;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Double getSolde() {
        return solde;
    }

    public Compte solde(Double solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public Compte typeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
        return this;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compte)) {
            return false;
        }
        return id != null && id.equals(((Compte) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            ", solde=" + getSolde() +
            ", typeCompte='" + getTypeCompte() + "'" +
            "}";
    }
}
