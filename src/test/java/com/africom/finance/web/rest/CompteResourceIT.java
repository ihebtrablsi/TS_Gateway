package com.africom.finance.web.rest;

import com.africom.finance.FinanceApp;
import com.africom.finance.domain.Compte;
import com.africom.finance.repository.CompteRepository;
import com.africom.finance.service.CompteService;
import com.africom.finance.service.dto.CompteDTO;
import com.africom.finance.service.mapper.CompteMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompteResource} REST controller.
 */
@SpringBootTest(classes = FinanceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompteResourceIT {

    private static final String DEFAULT_NUMERO_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMPTE = "BBBBBBBBBB";

    private static final Double DEFAULT_SOLDE = 1D;
    private static final Double UPDATED_SOLDE = 2D;

    private static final String DEFAULT_TYPE_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_COMPTE = "BBBBBBBBBB";

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;

    @Autowired
    private CompteService compteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompteMockMvc;

    private Compte compte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createEntity(EntityManager em) {
        Compte compte = new Compte()
            .numeroCompte(DEFAULT_NUMERO_COMPTE)
            .solde(DEFAULT_SOLDE)
            .typeCompte(DEFAULT_TYPE_COMPTE);
        return compte;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createUpdatedEntity(EntityManager em) {
        Compte compte = new Compte()
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .solde(UPDATED_SOLDE)
            .typeCompte(UPDATED_TYPE_COMPTE);
        return compte;
    }

    @BeforeEach
    public void initTest() {
        compte = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompte() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();
        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNumeroCompte()).isEqualTo(DEFAULT_NUMERO_COMPTE);
        assertThat(testCompte.getSolde()).isEqualTo(DEFAULT_SOLDE);
        assertThat(testCompte.getTypeCompte()).isEqualTo(DEFAULT_TYPE_COMPTE);
    }

    @Test
    @Transactional
    public void createCompteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte with an existing ID
        compte.setId(1L);
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroCompteIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteRepository.findAll().size();
        // set the field null
        compte.setNumeroCompte(null);

        // Create the Compte, which fails.
        CompteDTO compteDTO = compteMapper.toDto(compte);


        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSoldeIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteRepository.findAll().size();
        // set the field null
        compte.setSolde(null);

        // Create the Compte, which fails.
        CompteDTO compteDTO = compteMapper.toDto(compte);


        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComptes() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList
        restCompteMockMvc.perform(get("/api/comptes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE)))
            .andExpect(jsonPath("$.[*].solde").value(hasItem(DEFAULT_SOLDE.doubleValue())))
            .andExpect(jsonPath("$.[*].typeCompte").value(hasItem(DEFAULT_TYPE_COMPTE)));
    }
    
    @Test
    @Transactional
    public void getCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", compte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
            .andExpect(jsonPath("$.numeroCompte").value(DEFAULT_NUMERO_COMPTE))
            .andExpect(jsonPath("$.solde").value(DEFAULT_SOLDE.doubleValue()))
            .andExpect(jsonPath("$.typeCompte").value(DEFAULT_TYPE_COMPTE));
    }
    @Test
    @Transactional
    public void getNonExistingCompte() throws Exception {
        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte
        Compte updatedCompte = compteRepository.findById(compte.getId()).get();
        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
        em.detach(updatedCompte);
        updatedCompte
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .solde(UPDATED_SOLDE)
            .typeCompte(UPDATED_TYPE_COMPTE);
        CompteDTO compteDTO = compteMapper.toDto(updatedCompte);

        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNumeroCompte()).isEqualTo(UPDATED_NUMERO_COMPTE);
        assertThat(testCompte.getSolde()).isEqualTo(UPDATED_SOLDE);
        assertThat(testCompte.getTypeCompte()).isEqualTo(UPDATED_TYPE_COMPTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeDelete = compteRepository.findAll().size();

        // Delete the compte
        restCompteMockMvc.perform(delete("/api/comptes/{id}", compte.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
