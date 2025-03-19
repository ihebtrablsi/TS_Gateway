package com.africom.clientsales.web.rest;

import com.africom.clientsales.ClientSalesApp;
import com.africom.clientsales.domain.ActionCommerciale;
import com.africom.clientsales.repository.ActionCommercialeRepository;
import com.africom.clientsales.service.ActionCommercialeService;
import com.africom.clientsales.service.dto.ActionCommercialeDTO;
import com.africom.clientsales.service.mapper.ActionCommercialeMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ActionCommercialeResource} REST controller.
 */
@SpringBootTest(classes = ClientSalesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ActionCommercialeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TYPE_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private ActionCommercialeRepository actionCommercialeRepository;

    @Autowired
    private ActionCommercialeMapper actionCommercialeMapper;

    @Autowired
    private ActionCommercialeService actionCommercialeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActionCommercialeMockMvc;

    private ActionCommerciale actionCommerciale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionCommerciale createEntity(EntityManager em) {
        ActionCommerciale actionCommerciale = new ActionCommerciale()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .typeAction(DEFAULT_TYPE_ACTION)
            .statut(DEFAULT_STATUT);
        return actionCommerciale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionCommerciale createUpdatedEntity(EntityManager em) {
        ActionCommerciale actionCommerciale = new ActionCommerciale()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .typeAction(UPDATED_TYPE_ACTION)
            .statut(UPDATED_STATUT);
        return actionCommerciale;
    }

    @BeforeEach
    public void initTest() {
        actionCommerciale = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionCommerciale() throws Exception {
        int databaseSizeBeforeCreate = actionCommercialeRepository.findAll().size();
        // Create the ActionCommerciale
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);
        restActionCommercialeMockMvc.perform(post("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isCreated());

        // Validate the ActionCommerciale in the database
        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeCreate + 1);
        ActionCommerciale testActionCommerciale = actionCommercialeList.get(actionCommercialeList.size() - 1);
        assertThat(testActionCommerciale.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testActionCommerciale.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActionCommerciale.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testActionCommerciale.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testActionCommerciale.getTypeAction()).isEqualTo(DEFAULT_TYPE_ACTION);
        assertThat(testActionCommerciale.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createActionCommercialeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionCommercialeRepository.findAll().size();

        // Create the ActionCommerciale with an existing ID
        actionCommerciale.setId(1L);
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionCommercialeMockMvc.perform(post("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionCommerciale in the database
        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionCommercialeRepository.findAll().size();
        // set the field null
        actionCommerciale.setNom(null);

        // Create the ActionCommerciale, which fails.
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);


        restActionCommercialeMockMvc.perform(post("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isBadRequest());

        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionCommercialeRepository.findAll().size();
        // set the field null
        actionCommerciale.setDateDebut(null);

        // Create the ActionCommerciale, which fails.
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);


        restActionCommercialeMockMvc.perform(post("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isBadRequest());

        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionCommercialeRepository.findAll().size();
        // set the field null
        actionCommerciale.setDateFin(null);

        // Create the ActionCommerciale, which fails.
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);


        restActionCommercialeMockMvc.perform(post("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isBadRequest());

        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionCommercialeRepository.findAll().size();
        // set the field null
        actionCommerciale.setTypeAction(null);

        // Create the ActionCommerciale, which fails.
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);


        restActionCommercialeMockMvc.perform(post("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isBadRequest());

        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionCommercialeRepository.findAll().size();
        // set the field null
        actionCommerciale.setStatut(null);

        // Create the ActionCommerciale, which fails.
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);


        restActionCommercialeMockMvc.perform(post("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isBadRequest());

        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActionCommerciales() throws Exception {
        // Initialize the database
        actionCommercialeRepository.saveAndFlush(actionCommerciale);

        // Get all the actionCommercialeList
        restActionCommercialeMockMvc.perform(get("/api/action-commerciales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionCommerciale.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].typeAction").value(hasItem(DEFAULT_TYPE_ACTION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));
    }
    
    @Test
    @Transactional
    public void getActionCommerciale() throws Exception {
        // Initialize the database
        actionCommercialeRepository.saveAndFlush(actionCommerciale);

        // Get the actionCommerciale
        restActionCommercialeMockMvc.perform(get("/api/action-commerciales/{id}", actionCommerciale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actionCommerciale.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.typeAction").value(DEFAULT_TYPE_ACTION))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT));
    }
    @Test
    @Transactional
    public void getNonExistingActionCommerciale() throws Exception {
        // Get the actionCommerciale
        restActionCommercialeMockMvc.perform(get("/api/action-commerciales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionCommerciale() throws Exception {
        // Initialize the database
        actionCommercialeRepository.saveAndFlush(actionCommerciale);

        int databaseSizeBeforeUpdate = actionCommercialeRepository.findAll().size();

        // Update the actionCommerciale
        ActionCommerciale updatedActionCommerciale = actionCommercialeRepository.findById(actionCommerciale.getId()).get();
        // Disconnect from session so that the updates on updatedActionCommerciale are not directly saved in db
        em.detach(updatedActionCommerciale);
        updatedActionCommerciale
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .typeAction(UPDATED_TYPE_ACTION)
            .statut(UPDATED_STATUT);
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(updatedActionCommerciale);

        restActionCommercialeMockMvc.perform(put("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isOk());

        // Validate the ActionCommerciale in the database
        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeUpdate);
        ActionCommerciale testActionCommerciale = actionCommercialeList.get(actionCommercialeList.size() - 1);
        assertThat(testActionCommerciale.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testActionCommerciale.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActionCommerciale.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testActionCommerciale.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testActionCommerciale.getTypeAction()).isEqualTo(UPDATED_TYPE_ACTION);
        assertThat(testActionCommerciale.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingActionCommerciale() throws Exception {
        int databaseSizeBeforeUpdate = actionCommercialeRepository.findAll().size();

        // Create the ActionCommerciale
        ActionCommercialeDTO actionCommercialeDTO = actionCommercialeMapper.toDto(actionCommerciale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionCommercialeMockMvc.perform(put("/api/action-commerciales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionCommercialeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionCommerciale in the database
        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionCommerciale() throws Exception {
        // Initialize the database
        actionCommercialeRepository.saveAndFlush(actionCommerciale);

        int databaseSizeBeforeDelete = actionCommercialeRepository.findAll().size();

        // Delete the actionCommerciale
        restActionCommercialeMockMvc.perform(delete("/api/action-commerciales/{id}", actionCommerciale.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActionCommerciale> actionCommercialeList = actionCommercialeRepository.findAll();
        assertThat(actionCommercialeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
