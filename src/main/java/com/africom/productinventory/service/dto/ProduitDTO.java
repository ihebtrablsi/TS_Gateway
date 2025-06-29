package com.africom.productinventory.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.productinventory.domain.Produit} entity.
 */
public class ProduitDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String description;

    @NotNull
    @DecimalMin(value = "0")
    private Double prix;

    @NotNull
    @Min(value = 0)
    private Integer stock;

    private String categorie;

    private String imageUrl;

    private Object details;


    private Long familleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public Long getFamilleId() {
        return familleId;
    }

    public void setFamilleId(Long familleProduitId) {
        this.familleId = familleProduitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        return id != null && id.equals(((ProduitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", prix=" + getPrix() +
            ", stock=" + getStock() +
            ", categorie='" + getCategorie() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", details='" + getDetails() + "'" +
            ", familleId=" + getFamilleId() +
            "}";
    }
}
