package com.africom.productinventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Code.
 */
@Entity
@Table(name = "code")
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @NotNull
    @Column(name = "type_code")
    private String typeCode;

    @ManyToOne
    @JsonIgnoreProperties(value = "codes", allowSetters = true)
    private Produit produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Code code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public Code typeCode(String typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Produit getProduit() {
        return produit;
    }

    public Code produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Code)) {
            return false;
        }
        return id != null && id.equals(((Code) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Code{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", typeCode='" + getTypeCode() + "'" +
            "}";
    }
}
