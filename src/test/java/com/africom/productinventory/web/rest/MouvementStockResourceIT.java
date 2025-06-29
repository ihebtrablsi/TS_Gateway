package com.africom.productinventory.web.rest;

import com.africom.productinventory.ProductInventoryApp;
import com.africom.productinventory.domain.MouvementStock;
import com.africom.productinventory.repository.MouvementStockRepository;
import com.africom.productinventory.service.MouvementStockService;
import com.africom.productinventory.service.dto.MouvementStockDTO;
import com.africom.productinventory.service.mapper.MouvementStockMapper;

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
 * Integration tests for the {@link MouvementStockResource} REST controller.
 */
@SpringBootTest(classes = ProductInventoryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MouvementStockResourceIT {

    private static final String DEFAULT_TYPE_MOUVEMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_MOUVEMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITE = 0;
    private static final Integer UPDATED_QUANTITE = 1;

    private static final Instant DEFAULT_DATE_MOUVEMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MOUVEMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MouvementStockRepository mouvementStockRepository;

    @Autowired
    private MouvementStockMapper mouvementStockMapper;

    @Autowired
    private MouvementStockService mouvementStockService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMouvementStockMockMvc;

    private MouvementStock mouvementStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .typeMouvement(DEFAULT_TYPE_MOUVEMENT)
            .quantite(DEFAULT_QUANTITE)
            .dateMouvement(DEFAULT_DATE_MOUVEMENT);
        return mouvementStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createUpdatedEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .typeMouvement(UPDATED_TYPE_MOUVEMENT)
            .quantite(UPDATED_QUANTITE)
            .dateMouvement(UPDATED_DATE_MOUVEMENT);
        return mouvementStock;
    }

    @BeforeEach
    public void initTest() {
        mouvementStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createMouvementStock() throws Exception {
        int databaseSizeBeforeCreate = mouvementStockRepository.findAll().size();
        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);
        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isCreated());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeCreate + 1);
        MouvementStock testMouvementStock = mouvementStockList.get(mouvementStockList.size() - 1);
        assertThat(testMouvementStock.getTypeMouvement()).isEqualTo(DEFAULT_TYPE_MOUVEMENT);
        assertThat(testMouvementStock.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testMouvementStock.getDateMouvement()).isEqualTo(DEFAULT_DATE_MOUVEMENT);
    }

    @Test
    @Transactional
    public void createMouvementStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mouvementStockRepository.findAll().size();

        // Create the MouvementStock with an existing ID
        mouvementStock.setId(1L);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeMouvementIsRequired() throws Exception {
        int databaseSizeBeforeTest = mouvementStockRepository.findAll().size();
        // set the field null
        mouvementStock.setTypeMouvement(null);

        // Create the MouvementStock, which fails.
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);


        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = mouvementStockRepository.findAll().size();
        // set the field null
        mouvementStock.setQuantite(null);

        // Create the MouvementStock, which fails.
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);


        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateMouvementIsRequired() throws Exception {
        int databaseSizeBeforeTest = mouvementStockRepository.findAll().size();
        // set the field null
        mouvementStock.setDateMouvement(null);

        // Create the MouvementStock, which fails.
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);


        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMouvementStocks() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeMouvement").value(hasItem(DEFAULT_TYPE_MOUVEMENT)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].dateMouvement").value(hasItem(DEFAULT_DATE_MOUVEMENT.toString())));
    }

    @Test
    @Transactional
    public void getMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/{id}", mouvementStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mouvementStock.getId().intValue()))
            .andExpect(jsonPath("$.typeMouvement").value(DEFAULT_TYPE_MOUVEMENT))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.dateMouvement").value(DEFAULT_DATE_MOUVEMENT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingMouvementStock() throws Exception {
        // Get the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        int databaseSizeBeforeUpdate = mouvementStockRepository.findAll().size();

        // Update the mouvementStock
        MouvementStock updatedMouvementStock = mouvementStockRepository.findById(mouvementStock.getId()).get();
        // Disconnect from session so that the updates on updatedMouvementStock are not directly saved in db
        em.detach(updatedMouvementStock);
        updatedMouvementStock
            .typeMouvement(UPDATED_TYPE_MOUVEMENT)
            .quantite(UPDATED_QUANTITE)
            .dateMouvement(UPDATED_DATE_MOUVEMENT);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(updatedMouvementStock);

        restMouvementStockMockMvc.perform(put("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isOk());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeUpdate);
        MouvementStock testMouvementStock = mouvementStockList.get(mouvementStockList.size() - 1);
        assertThat(testMouvementStock.getTypeMouvement()).isEqualTo(UPDATED_TYPE_MOUVEMENT);
        assertThat(testMouvementStock.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testMouvementStock.getDateMouvement()).isEqualTo(UPDATED_DATE_MOUVEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingMouvementStock() throws Exception {
        int databaseSizeBeforeUpdate = mouvementStockRepository.findAll().size();

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc.perform(put("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        int databaseSizeBeforeDelete = mouvementStockRepository.findAll().size();

        // Delete the mouvementStock
        restMouvementStockMockMvc.perform(delete("/api/mouvement-stocks/{id}", mouvementStock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
