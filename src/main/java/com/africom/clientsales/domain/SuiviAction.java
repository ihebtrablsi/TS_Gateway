package com.africom.clientsales.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SuiviAction.
 */
@Entity
@Table(name = "suivi_action")
public class SuiviAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_suivi", nullable = false)
    private Instant dateSuivi;

    @NotNull
    @Column(name = "indicateurs", nullable = false)
    private String indicateurs;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties(value = "suiviActions", allowSetters = true)
    private ActionCommerciale actionCommerciale;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateSuivi() {
        return dateSuivi;
    }

    public SuiviAction dateSuivi(Instant dateSuivi) {
        this.dateSuivi = dateSuivi;
        return this;
    }

    public void setDateSuivi(Instant dateSuivi) {
        this.dateSuivi = dateSuivi;
    }

    public String getIndicateurs() {
        return indicateurs;
    }

    public SuiviAction indicateurs(String indicateurs) {
        this.indicateurs = indicateurs;
        return this;
    }

    public void setIndicateurs(String indicateurs) {
        this.indicateurs = indicateurs;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public SuiviAction commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public ActionCommerciale getActionCommerciale() {
        return actionCommerciale;
    }

    public SuiviAction actionCommerciale(ActionCommerciale actionCommerciale) {
        this.actionCommerciale = actionCommerciale;
        return this;
    }

    public void setActionCommerciale(ActionCommerciale actionCommerciale) {
        this.actionCommerciale = actionCommerciale;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SuiviAction)) {
            return false;
        }
        return id != null && id.equals(((SuiviAction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SuiviAction{" +
            "id=" + getId() +
            ", dateSuivi='" + getDateSuivi() + "'" +
            ", indicateurs='" + getIndicateurs() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
