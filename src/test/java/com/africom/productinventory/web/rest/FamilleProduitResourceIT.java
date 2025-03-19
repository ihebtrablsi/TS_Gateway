package com.africom.productinventory.web.rest;

import com.africom.productinventory.ProductInventoryApp;
import com.africom.productinventory.domain.FamilleProduit;
import com.africom.productinventory.repository.FamilleProduitRepository;
import com.africom.productinventory.service.FamilleProduitService;
import com.africom.productinventory.service.dto.FamilleProduitDTO;
import com.africom.productinventory.service.mapper.FamilleProduitMapper;

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
 * Integration tests for the {@link FamilleProduitResource} REST controller.
 */
@SpringBootTest(classes = ProductInventoryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FamilleProduitResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS = "BBBBBBBBBB";

    @Autowired
    private FamilleProduitRepository familleProduitRepository;

    @Autowired
    private FamilleProduitMapper familleProduitMapper;

    @Autowired
    private FamilleProduitService familleProduitService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilleProduitMockMvc;

    private FamilleProduit familleProduit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilleProduit createEntity(EntityManager em) {
        FamilleProduit familleProduit = new FamilleProduit()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .options(DEFAULT_OPTIONS);
        return familleProduit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilleProduit createUpdatedEntity(EntityManager em) {
        FamilleProduit familleProduit = new FamilleProduit()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .options(UPDATED_OPTIONS);
        return familleProduit;
    }

    @BeforeEach
    public void initTest() {
        familleProduit = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilleProduit() throws Exception {
        int databaseSizeBeforeCreate = familleProduitRepository.findAll().size();
        // Create the FamilleProduit
        FamilleProduitDTO familleProduitDTO = familleProduitMapper.toDto(familleProduit);
        restFamilleProduitMockMvc.perform(post("/api/famille-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familleProduitDTO)))
            .andExpect(status().isCreated());

        // Validate the FamilleProduit in the database
        List<FamilleProduit> familleProduitList = familleProduitRepository.findAll();
        assertThat(familleProduitList).hasSize(databaseSizeBeforeCreate + 1);
        FamilleProduit testFamilleProduit = familleProduitList.get(familleProduitList.size() - 1);
        assertThat(testFamilleProduit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFamilleProduit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFamilleProduit.getOptions()).isEqualTo(DEFAULT_OPTIONS);
    }

    @Test
    @Transactional
    public void createFamilleProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familleProduitRepository.findAll().size();

        // Create the FamilleProduit with an existing ID
        familleProduit.setId(1L);
        FamilleProduitDTO familleProduitDTO = familleProduitMapper.toDto(familleProduit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilleProduitMockMvc.perform(post("/api/famille-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familleProduitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamilleProduit in the database
        List<FamilleProduit> familleProduitList = familleProduitRepository.findAll();
        assertThat(familleProduitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = familleProduitRepository.findAll().size();
        // set the field null
        familleProduit.setNom(null);

        // Create the FamilleProduit, which fails.
        FamilleProduitDTO familleProduitDTO = familleProduitMapper.toDto(familleProduit);


        restFamilleProduitMockMvc.perform(post("/api/famille-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familleProduitDTO)))
            .andExpect(status().isBadRequest());

        List<FamilleProduit> familleProduitList = familleProduitRepository.findAll();
        assertThat(familleProduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFamilleProduits() throws Exception {
        // Initialize the database
        familleProduitRepository.saveAndFlush(familleProduit);

        // Get all the familleProduitList
        restFamilleProduitMockMvc.perform(get("/api/famille-produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familleProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS)));
    }
    
    @Test
    @Transactional
    public void getFamilleProduit() throws Exception {
        // Initialize the database
        familleProduitRepository.saveAndFlush(familleProduit);

        // Get the familleProduit
        restFamilleProduitMockMvc.perform(get("/api/famille-produits/{id}", familleProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familleProduit.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS));
    }
    @Test
    @Transactional
    public void getNonExistingFamilleProduit() throws Exception {
        // Get the familleProduit
        restFamilleProduitMockMvc.perform(get("/api/famille-produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilleProduit() throws Exception {
        // Initialize the database
        familleProduitRepository.saveAndFlush(familleProduit);

        int databaseSizeBeforeUpdate = familleProduitRepository.findAll().size();

        // Update the familleProduit
        FamilleProduit updatedFamilleProduit = familleProduitRepository.findById(familleProduit.getId()).get();
        // Disconnect from session so that the updates on updatedFamilleProduit are not directly saved in db
        em.detach(updatedFamilleProduit);
        updatedFamilleProduit
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .options(UPDATED_OPTIONS);
        FamilleProduitDTO familleProduitDTO = familleProduitMapper.toDto(updatedFamilleProduit);

        restFamilleProduitMockMvc.perform(put("/api/famille-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familleProduitDTO)))
            .andExpect(status().isOk());

        // Validate the FamilleProduit in the database
        List<FamilleProduit> familleProduitList = familleProduitRepository.findAll();
        assertThat(familleProduitList).hasSize(databaseSizeBeforeUpdate);
        FamilleProduit testFamilleProduit = familleProduitList.get(familleProduitList.size() - 1);
        assertThat(testFamilleProduit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFamilleProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFamilleProduit.getOptions()).isEqualTo(UPDATED_OPTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilleProduit() throws Exception {
        int databaseSizeBeforeUpdate = familleProduitRepository.findAll().size();

        // Create the FamilleProduit
        FamilleProduitDTO familleProduitDTO = familleProduitMapper.toDto(familleProduit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilleProduitMockMvc.perform(put("/api/famille-produits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familleProduitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamilleProduit in the database
        List<FamilleProduit> familleProduitList = familleProduitRepository.findAll();
        assertThat(familleProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamilleProduit() throws Exception {
        // Initialize the database
        familleProduitRepository.saveAndFlush(familleProduit);

        int databaseSizeBeforeDelete = familleProduitRepository.findAll().size();

        // Delete the familleProduit
        restFamilleProduitMockMvc.perform(delete("/api/famille-produits/{id}", familleProduit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilleProduit> familleProduitList = familleProduitRepository.findAll();
        assertThat(familleProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
