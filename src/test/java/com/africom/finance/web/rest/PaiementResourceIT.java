package com.africom.finance.web.rest;

import com.africom.finance.FinanceApp;
import com.africom.finance.domain.Paiement;
import com.africom.finance.repository.PaiementRepository;
import com.africom.finance.service.PaiementService;
import com.africom.finance.service.dto.PaiementDTO;
import com.africom.finance.service.mapper.PaiementMapper;

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
 * Integration tests for the {@link PaiementResource} REST controller.
 */
@SpringBootTest(classes = FinanceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaiementResourceIT {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_PAIEMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PAIEMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_MONTANT = 0D;
    private static final Double UPDATED_MONTANT = 1D;

    private static final String DEFAULT_MODE_PAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_MODE_PAIEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private PaiementMapper paiementMapper;

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaiementMockMvc;

    private Paiement paiement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiement createEntity(EntityManager em) {
        Paiement paiement = new Paiement()
            .reference(DEFAULT_REFERENCE)
            .datePaiement(DEFAULT_DATE_PAIEMENT)
            .montant(DEFAULT_MONTANT)
            .modePaiement(DEFAULT_MODE_PAIEMENT)
            .statut(DEFAULT_STATUT);
        return paiement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiement createUpdatedEntity(EntityManager em) {
        Paiement paiement = new Paiement()
            .reference(UPDATED_REFERENCE)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .modePaiement(UPDATED_MODE_PAIEMENT)
            .statut(UPDATED_STATUT);
        return paiement;
    }

    @BeforeEach
    public void initTest() {
        paiement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaiement() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();
        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isCreated());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate + 1);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testPaiement.getDatePaiement()).isEqualTo(DEFAULT_DATE_PAIEMENT);
        assertThat(testPaiement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testPaiement.getModePaiement()).isEqualTo(DEFAULT_MODE_PAIEMENT);
        assertThat(testPaiement.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createPaiementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();

        // Create the Paiement with an existing ID
        paiement.setId(1L);
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementRepository.findAll().size();
        // set the field null
        paiement.setReference(null);

        // Create the Paiement, which fails.
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);


        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatePaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementRepository.findAll().size();
        // set the field null
        paiement.setDatePaiement(null);

        // Create the Paiement, which fails.
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);


        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementRepository.findAll().size();
        // set the field null
        paiement.setMontant(null);

        // Create the Paiement, which fails.
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);


        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModePaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementRepository.findAll().size();
        // set the field null
        paiement.setModePaiement(null);

        // Create the Paiement, which fails.
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);


        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementRepository.findAll().size();
        // set the field null
        paiement.setStatut(null);

        // Create the Paiement, which fails.
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);


        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaiements() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        // Get all the paiementList
        restPaiementMockMvc.perform(get("/api/paiements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].datePaiement").value(hasItem(DEFAULT_DATE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].modePaiement").value(hasItem(DEFAULT_MODE_PAIEMENT)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));
    }
    
    @Test
    @Transactional
    public void getPaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", paiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paiement.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.datePaiement").value(DEFAULT_DATE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.modePaiement").value(DEFAULT_MODE_PAIEMENT))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT));
    }
    @Test
    @Transactional
    public void getNonExistingPaiement() throws Exception {
        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Update the paiement
        Paiement updatedPaiement = paiementRepository.findById(paiement.getId()).get();
        // Disconnect from session so that the updates on updatedPaiement are not directly saved in db
        em.detach(updatedPaiement);
        updatedPaiement
            .reference(UPDATED_REFERENCE)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .montant(UPDATED_MONTANT)
            .modePaiement(UPDATED_MODE_PAIEMENT)
            .statut(UPDATED_STATUT);
        PaiementDTO paiementDTO = paiementMapper.toDto(updatedPaiement);

        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isOk());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testPaiement.getDatePaiement()).isEqualTo(UPDATED_DATE_PAIEMENT);
        assertThat(testPaiement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testPaiement.getModePaiement()).isEqualTo(UPDATED_MODE_PAIEMENT);
        assertThat(testPaiement.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingPaiement() throws Exception {
        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        int databaseSizeBeforeDelete = paiementRepository.findAll().size();

        // Delete the paiement
        restPaiementMockMvc.perform(delete("/api/paiements/{id}", paiement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
