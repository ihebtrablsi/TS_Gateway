package com.africom.finance.web.rest;

import com.africom.finance.FinanceApp;
import com.africom.finance.domain.Facture;
import com.africom.finance.repository.FactureRepository;
import com.africom.finance.service.FactureService;
import com.africom.finance.service.dto.FactureDTO;
import com.africom.finance.service.mapper.FactureMapper;

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
 * Integration tests for the {@link FactureResource} REST controller.
 */
@SpringBootTest(classes = FinanceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FactureResourceIT {

    private static final String DEFAULT_NUMERO_FACTURE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FACTURE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_FACTURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FACTURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_MONTANT_TOTAL = 0D;
    private static final Double UPDATED_MONTANT_TOTAL = 1D;

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ECHEANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ECHEANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureMapper factureMapper;

    @Autowired
    private FactureService factureService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureMockMvc;

    private Facture facture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createEntity(EntityManager em) {
        Facture facture = new Facture()
            .numeroFacture(DEFAULT_NUMERO_FACTURE)
            .dateFacture(DEFAULT_DATE_FACTURE)
            .montantTotal(DEFAULT_MONTANT_TOTAL)
            .statut(DEFAULT_STATUT)
            .dateEcheance(DEFAULT_DATE_ECHEANCE);
        return facture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createUpdatedEntity(EntityManager em) {
        Facture facture = new Facture()
            .numeroFacture(UPDATED_NUMERO_FACTURE)
            .dateFacture(UPDATED_DATE_FACTURE)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .statut(UPDATED_STATUT)
            .dateEcheance(UPDATED_DATE_ECHEANCE);
        return facture;
    }

    @BeforeEach
    public void initTest() {
        facture = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacture() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();
        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isCreated());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate + 1);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getNumeroFacture()).isEqualTo(DEFAULT_NUMERO_FACTURE);
        assertThat(testFacture.getDateFacture()).isEqualTo(DEFAULT_DATE_FACTURE);
        assertThat(testFacture.getMontantTotal()).isEqualTo(DEFAULT_MONTANT_TOTAL);
        assertThat(testFacture.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testFacture.getDateEcheance()).isEqualTo(DEFAULT_DATE_ECHEANCE);
    }

    @Test
    @Transactional
    public void createFactureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();

        // Create the Facture with an existing ID
        facture.setId(1L);
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroFactureIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setNumeroFacture(null);

        // Create the Facture, which fails.
        FactureDTO factureDTO = factureMapper.toDto(facture);


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFactureIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setDateFacture(null);

        // Create the Facture, which fails.
        FactureDTO factureDTO = factureMapper.toDto(facture);


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setMontantTotal(null);

        // Create the Facture, which fails.
        FactureDTO factureDTO = factureMapper.toDto(facture);


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setStatut(null);

        // Create the Facture, which fails.
        FactureDTO factureDTO = factureMapper.toDto(facture);


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFactures() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList
        restFactureMockMvc.perform(get("/api/factures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facture.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroFacture").value(hasItem(DEFAULT_NUMERO_FACTURE)))
            .andExpect(jsonPath("$.[*].dateFacture").value(hasItem(DEFAULT_DATE_FACTURE.toString())))
            .andExpect(jsonPath("$.[*].montantTotal").value(hasItem(DEFAULT_MONTANT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].dateEcheance").value(hasItem(DEFAULT_DATE_ECHEANCE.toString())));
    }
    
    @Test
    @Transactional
    public void getFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", facture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facture.getId().intValue()))
            .andExpect(jsonPath("$.numeroFacture").value(DEFAULT_NUMERO_FACTURE))
            .andExpect(jsonPath("$.dateFacture").value(DEFAULT_DATE_FACTURE.toString()))
            .andExpect(jsonPath("$.montantTotal").value(DEFAULT_MONTANT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.dateEcheance").value(DEFAULT_DATE_ECHEANCE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFacture() throws Exception {
        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Update the facture
        Facture updatedFacture = factureRepository.findById(facture.getId()).get();
        // Disconnect from session so that the updates on updatedFacture are not directly saved in db
        em.detach(updatedFacture);
        updatedFacture
            .numeroFacture(UPDATED_NUMERO_FACTURE)
            .dateFacture(UPDATED_DATE_FACTURE)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .statut(UPDATED_STATUT)
            .dateEcheance(UPDATED_DATE_ECHEANCE);
        FactureDTO factureDTO = factureMapper.toDto(updatedFacture);

        restFactureMockMvc.perform(put("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isOk());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getNumeroFacture()).isEqualTo(UPDATED_NUMERO_FACTURE);
        assertThat(testFacture.getDateFacture()).isEqualTo(UPDATED_DATE_FACTURE);
        assertThat(testFacture.getMontantTotal()).isEqualTo(UPDATED_MONTANT_TOTAL);
        assertThat(testFacture.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testFacture.getDateEcheance()).isEqualTo(UPDATED_DATE_ECHEANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureMockMvc.perform(put("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        int databaseSizeBeforeDelete = factureRepository.findAll().size();

        // Delete the facture
        restFactureMockMvc.perform(delete("/api/factures/{id}", facture.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
