package com.africom.productinventory.domain;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Map;

/**
 * A FamilleProduit.
 */
@Entity
@Table(name = "famille_produit")
@TypeDefs({
    @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
public class FamilleProduit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb",name = "options")
//    private Map<String, Object> options;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb",name = "options")
    private Object options;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public FamilleProduit nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public FamilleProduit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getOptions() {
        return options;
    }

    public FamilleProduit options(Object options) {
        this.options = options;
        return this;
    }

    public void setOptions(Object options) {
        this.options = options;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilleProduit)) {
            return false;
        }
        return id != null && id.equals(((FamilleProduit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilleProduit{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", options='" + getOptions() + "'" +
            "}";
    }
}
