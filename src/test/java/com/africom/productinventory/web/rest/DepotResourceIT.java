package com.africom.productinventory.web.rest;

import com.africom.productinventory.ProductInventoryApp;
import com.africom.productinventory.domain.Depot;
import com.africom.productinventory.repository.DepotRepository;
import com.africom.productinventory.service.DepotService;
import com.africom.productinventory.service.dto.DepotDTO;
import com.africom.productinventory.service.mapper.DepotMapper;

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
 * Integration tests for the {@link DepotResource} REST controller.
 */
@SpringBootTest(classes = ProductInventoryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepotResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DEPOT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITE = 0;
    private static final Integer UPDATED_CAPACITE = 1;

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private DepotMapper depotMapper;

    @Autowired
    private DepotService depotService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepotMockMvc;

    private Depot depot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createEntity(EntityManager em) {
        Depot depot = new Depot()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .typeDepot(DEFAULT_TYPE_DEPOT)
            .capacite(DEFAULT_CAPACITE);
        return depot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createUpdatedEntity(EntityManager em) {
        Depot depot = new Depot()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .typeDepot(UPDATED_TYPE_DEPOT)
            .capacite(UPDATED_CAPACITE);
        return depot;
    }

    @BeforeEach
    public void initTest() {
        depot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepot() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();
        // Create the Depot
        DepotDTO depotDTO = depotMapper.toDto(depot);
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isCreated());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate + 1);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDepot.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDepot.getTypeDepot()).isEqualTo(DEFAULT_TYPE_DEPOT);
        assertThat(testDepot.getCapacite()).isEqualTo(DEFAULT_CAPACITE);
    }

    @Test
    @Transactional
    public void createDepotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot with an existing ID
        depot.setId(1L);
        DepotDTO depotDTO = depotMapper.toDto(depot);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotRepository.findAll().size();
        // set the field null
        depot.setNom(null);

        // Create the Depot, which fails.
        DepotDTO depotDTO = depotMapper.toDto(depot);


        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isBadRequest());

        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotRepository.findAll().size();
        // set the field null
        depot.setAdresse(null);

        // Create the Depot, which fails.
        DepotDTO depotDTO = depotMapper.toDto(depot);


        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isBadRequest());

        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeDepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotRepository.findAll().size();
        // set the field null
        depot.setTypeDepot(null);

        // Create the Depot, which fails.
        DepotDTO depotDTO = depotMapper.toDto(depot);


        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isBadRequest());

        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapaciteIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotRepository.findAll().size();
        // set the field null
        depot.setCapacite(null);

        // Create the Depot, which fails.
        DepotDTO depotDTO = depotMapper.toDto(depot);


        restDepotMockMvc.perform(post("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isBadRequest());

        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepots() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].typeDepot").value(hasItem(DEFAULT_TYPE_DEPOT)))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)));
    }
    
    @Test
    @Transactional
    public void getDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", depot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(depot.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.typeDepot").value(DEFAULT_TYPE_DEPOT))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE));
    }
    @Test
    @Transactional
    public void getNonExistingDepot() throws Exception {
        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Update the depot
        Depot updatedDepot = depotRepository.findById(depot.getId()).get();
        // Disconnect from session so that the updates on updatedDepot are not directly saved in db
        em.detach(updatedDepot);
        updatedDepot
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .typeDepot(UPDATED_TYPE_DEPOT)
            .capacite(UPDATED_CAPACITE);
        DepotDTO depotDTO = depotMapper.toDto(updatedDepot);

        restDepotMockMvc.perform(put("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isOk());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDepot.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDepot.getTypeDepot()).isEqualTo(UPDATED_TYPE_DEPOT);
        assertThat(testDepot.getCapacite()).isEqualTo(UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    public void updateNonExistingDepot() throws Exception {
        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Create the Depot
        DepotDTO depotDTO = depotMapper.toDto(depot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotMockMvc.perform(put("/api/depots")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        int databaseSizeBeforeDelete = depotRepository.findAll().size();

        // Delete the depot
        restDepotMockMvc.perform(delete("/api/depots/{id}", depot.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
