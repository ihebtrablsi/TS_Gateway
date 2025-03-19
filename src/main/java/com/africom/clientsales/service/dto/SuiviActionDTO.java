package com.africom.clientsales.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.africom.clientsales.domain.SuiviAction} entity.
 */
public class SuiviActionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant dateSuivi;

    @NotNull
    private String indicateurs;

    private String commentaire;


    private Long actionCommercialeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateSuivi() {
        return dateSuivi;
    }

    public void setDateSuivi(Instant dateSuivi) {
        this.dateSuivi = dateSuivi;
    }

    public String getIndicateurs() {
        return indicateurs;
    }

    public void setIndicateurs(String indicateurs) {
        this.indicateurs = indicateurs;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getActionCommercialeId() {
        return actionCommercialeId;
    }

    public void setActionCommercialeId(Long actionCommercialeId) {
        this.actionCommercialeId = actionCommercialeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SuiviActionDTO)) {
            return false;
        }

        return id != null && id.equals(((SuiviActionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuiviActionDTO{" +
            "id=" + getId() +
            ", dateSuivi='" + getDateSuivi() + "'" +
            ", indicateurs='" + getIndicateurs() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", actionCommercialeId=" + getActionCommercialeId() +
            "}";
    }
}
