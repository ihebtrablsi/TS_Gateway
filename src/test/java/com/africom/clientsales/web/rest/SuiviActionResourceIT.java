package com.africom.clientsales.web.rest;

import com.africom.clientsales.ClientSalesApp;
import com.africom.clientsales.domain.SuiviAction;
import com.africom.clientsales.repository.SuiviActionRepository;
import com.africom.clientsales.service.SuiviActionService;
import com.africom.clientsales.service.dto.SuiviActionDTO;
import com.africom.clientsales.service.mapper.SuiviActionMapper;

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
 * Integration tests for the {@link SuiviActionResource} REST controller.
 */
@SpringBootTest(classes = ClientSalesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SuiviActionResourceIT {

    private static final Instant DEFAULT_DATE_SUIVI = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_SUIVI = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INDICATEURS = "AAAAAAAAAA";
    private static final String UPDATED_INDICATEURS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private SuiviActionRepository suiviActionRepository;

    @Autowired
    private SuiviActionMapper suiviActionMapper;

    @Autowired
    private SuiviActionService suiviActionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuiviActionMockMvc;

    private SuiviAction suiviAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SuiviAction createEntity(EntityManager em) {
        SuiviAction suiviAction = new SuiviAction()
            .dateSuivi(DEFAULT_DATE_SUIVI)
            .indicateurs(DEFAULT_INDICATEURS)
            .commentaire(DEFAULT_COMMENTAIRE);
        return suiviAction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SuiviAction createUpdatedEntity(EntityManager em) {
        SuiviAction suiviAction = new SuiviAction()
            .dateSuivi(UPDATED_DATE_SUIVI)
            .indicateurs(UPDATED_INDICATEURS)
            .commentaire(UPDATED_COMMENTAIRE);
        return suiviAction;
    }

    @BeforeEach
    public void initTest() {
        suiviAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuiviAction() throws Exception {
        int databaseSizeBeforeCreate = suiviActionRepository.findAll().size();
        // Create the SuiviAction
        SuiviActionDTO suiviActionDTO = suiviActionMapper.toDto(suiviAction);
        restSuiviActionMockMvc.perform(post("/api/suivi-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suiviActionDTO)))
            .andExpect(status().isCreated());

        // Validate the SuiviAction in the database
        List<SuiviAction> suiviActionList = suiviActionRepository.findAll();
        assertThat(suiviActionList).hasSize(databaseSizeBeforeCreate + 1);
        SuiviAction testSuiviAction = suiviActionList.get(suiviActionList.size() - 1);
        assertThat(testSuiviAction.getDateSuivi()).isEqualTo(DEFAULT_DATE_SUIVI);
        assertThat(testSuiviAction.getIndicateurs()).isEqualTo(DEFAULT_INDICATEURS);
        assertThat(testSuiviAction.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createSuiviActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suiviActionRepository.findAll().size();

        // Create the SuiviAction with an existing ID
        suiviAction.setId(1L);
        SuiviActionDTO suiviActionDTO = suiviActionMapper.toDto(suiviAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuiviActionMockMvc.perform(post("/api/suivi-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suiviActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SuiviAction in the database
        List<SuiviAction> suiviActionList = suiviActionRepository.findAll();
        assertThat(suiviActionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateSuiviIsRequired() throws Exception {
        int databaseSizeBeforeTest = suiviActionRepository.findAll().size();
        // set the field null
        suiviAction.setDateSuivi(null);

        // Create the SuiviAction, which fails.
        SuiviActionDTO suiviActionDTO = suiviActionMapper.toDto(suiviAction);


        restSuiviActionMockMvc.perform(post("/api/suivi-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suiviActionDTO)))
            .andExpect(status().isBadRequest());

        List<SuiviAction> suiviActionList = suiviActionRepository.findAll();
        assertThat(suiviActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndicateursIsRequired() throws Exception {
        int databaseSizeBeforeTest = suiviActionRepository.findAll().size();
        // set the field null
        suiviAction.setIndicateurs(null);

        // Create the SuiviAction, which fails.
        SuiviActionDTO suiviActionDTO = suiviActionMapper.toDto(suiviAction);


        restSuiviActionMockMvc.perform(post("/api/suivi-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suiviActionDTO)))
            .andExpect(status().isBadRequest());

        List<SuiviAction> suiviActionList = suiviActionRepository.findAll();
        assertThat(suiviActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuiviActions() throws Exception {
        // Initialize the database
        suiviActionRepository.saveAndFlush(suiviAction);

        // Get all the suiviActionList
        restSuiviActionMockMvc.perform(get("/api/suivi-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suiviAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateSuivi").value(hasItem(DEFAULT_DATE_SUIVI.toString())))
            .andExpect(jsonPath("$.[*].indicateurs").value(hasItem(DEFAULT_INDICATEURS)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getSuiviAction() throws Exception {
        // Initialize the database
        suiviActionRepository.saveAndFlush(suiviAction);

        // Get the suiviAction
        restSuiviActionMockMvc.perform(get("/api/suivi-actions/{id}", suiviAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(suiviAction.getId().intValue()))
            .andExpect(jsonPath("$.dateSuivi").value(DEFAULT_DATE_SUIVI.toString()))
            .andExpect(jsonPath("$.indicateurs").value(DEFAULT_INDICATEURS))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }
    @Test
    @Transactional
    public void getNonExistingSuiviAction() throws Exception {
        // Get the suiviAction
        restSuiviActionMockMvc.perform(get("/api/suivi-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuiviAction() throws Exception {
        // Initialize the database
        suiviActionRepository.saveAndFlush(suiviAction);

        int databaseSizeBeforeUpdate = suiviActionRepository.findAll().size();

        // Update the suiviAction
        SuiviAction updatedSuiviAction = suiviActionRepository.findById(suiviAction.getId()).get();
        // Disconnect from session so that the updates on updatedSuiviAction are not directly saved in db
        em.detach(updatedSuiviAction);
        updatedSuiviAction
            .dateSuivi(UPDATED_DATE_SUIVI)
            .indicateurs(UPDATED_INDICATEURS)
            .commentaire(UPDATED_COMMENTAIRE);
        SuiviActionDTO suiviActionDTO = suiviActionMapper.toDto(updatedSuiviAction);

        restSuiviActionMockMvc.perform(put("/api/suivi-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suiviActionDTO)))
            .andExpect(status().isOk());

        // Validate the SuiviAction in the database
        List<SuiviAction> suiviActionList = suiviActionRepository.findAll();
        assertThat(suiviActionList).hasSize(databaseSizeBeforeUpdate);
        SuiviAction testSuiviAction = suiviActionList.get(suiviActionList.size() - 1);
        assertThat(testSuiviAction.getDateSuivi()).isEqualTo(UPDATED_DATE_SUIVI);
        assertThat(testSuiviAction.getIndicateurs()).isEqualTo(UPDATED_INDICATEURS);
        assertThat(testSuiviAction.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingSuiviAction() throws Exception {
        int databaseSizeBeforeUpdate = suiviActionRepository.findAll().size();

        // Create the SuiviAction
        SuiviActionDTO suiviActionDTO = suiviActionMapper.toDto(suiviAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuiviActionMockMvc.perform(put("/api/suivi-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suiviActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SuiviAction in the database
        List<SuiviAction> suiviActionList = suiviActionRepository.findAll();
        assertThat(suiviActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuiviAction() throws Exception {
        // Initialize the database
        suiviActionRepository.saveAndFlush(suiviAction);

        int databaseSizeBeforeDelete = suiviActionRepository.findAll().size();

        // Delete the suiviAction
        restSuiviActionMockMvc.perform(delete("/api/suivi-actions/{id}", suiviAction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SuiviAction> suiviActionList = suiviActionRepository.findAll();
        assertThat(suiviActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
