package com.africom.finance.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.finance.domain.Compte} entity.
 */
public class CompteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String numeroCompte;

    @NotNull
    private Double solde;

    private String typeCompte;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteDTO)) {
            return false;
        }

        return id != null && id.equals(((CompteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompteDTO{" +
            "id=" + getId() +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            ", solde=" + getSolde() +
            ", typeCompte='" + getTypeCompte() + "'" +
            "}";
    }
}
