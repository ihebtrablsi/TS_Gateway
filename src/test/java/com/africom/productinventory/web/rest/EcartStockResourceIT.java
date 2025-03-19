package com.africom.productinventory.web.rest;

import com.africom.productinventory.ProductInventoryApp;
import com.africom.productinventory.domain.EcartStock;
import com.africom.productinventory.repository.EcartStockRepository;
import com.africom.productinventory.service.EcartStockService;
import com.africom.productinventory.service.dto.EcartStockDTO;
import com.africom.productinventory.service.mapper.EcartStockMapper;

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
 * Integration tests for the {@link EcartStockResource} REST controller.
 */
@SpringBootTest(classes = ProductInventoryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EcartStockResourceIT {

    private static final Integer DEFAULT_QUANTITE_THEORIQUE = 1;
    private static final Integer UPDATED_QUANTITE_THEORIQUE = 2;

    private static final Integer DEFAULT_QUANTITE_PHYSIQUE = 1;
    private static final Integer UPDATED_QUANTITE_PHYSIQUE = 2;

    private static final Integer DEFAULT_ECART = 1;
    private static final Integer UPDATED_ECART = 2;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private EcartStockRepository ecartStockRepository;

    @Autowired
    private EcartStockMapper ecartStockMapper;

    @Autowired
    private EcartStockService ecartStockService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcartStockMockMvc;

    private EcartStock ecartStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcartStock createEntity(EntityManager em) {
        EcartStock ecartStock = new EcartStock()
            .quantiteTheorique(DEFAULT_QUANTITE_THEORIQUE)
            .quantitePhysique(DEFAULT_QUANTITE_PHYSIQUE)
            .ecart(DEFAULT_ECART)
            .commentaire(DEFAULT_COMMENTAIRE);
        return ecartStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcartStock createUpdatedEntity(EntityManager em) {
        EcartStock ecartStock = new EcartStock()
            .quantiteTheorique(UPDATED_QUANTITE_THEORIQUE)
            .quantitePhysique(UPDATED_QUANTITE_PHYSIQUE)
            .ecart(UPDATED_ECART)
            .commentaire(UPDATED_COMMENTAIRE);
        return ecartStock;
    }

    @BeforeEach
    public void initTest() {
        ecartStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createEcartStock() throws Exception {
        int databaseSizeBeforeCreate = ecartStockRepository.findAll().size();
        // Create the EcartStock
        EcartStockDTO ecartStockDTO = ecartStockMapper.toDto(ecartStock);
        restEcartStockMockMvc.perform(post("/api/ecart-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecartStockDTO)))
            .andExpect(status().isCreated());

        // Validate the EcartStock in the database
        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeCreate + 1);
        EcartStock testEcartStock = ecartStockList.get(ecartStockList.size() - 1);
        assertThat(testEcartStock.getQuantiteTheorique()).isEqualTo(DEFAULT_QUANTITE_THEORIQUE);
        assertThat(testEcartStock.getQuantitePhysique()).isEqualTo(DEFAULT_QUANTITE_PHYSIQUE);
        assertThat(testEcartStock.getEcart()).isEqualTo(DEFAULT_ECART);
        assertThat(testEcartStock.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createEcartStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ecartStockRepository.findAll().size();

        // Create the EcartStock with an existing ID
        ecartStock.setId(1L);
        EcartStockDTO ecartStockDTO = ecartStockMapper.toDto(ecartStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcartStockMockMvc.perform(post("/api/ecart-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecartStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EcartStock in the database
        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantiteTheoriqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecartStockRepository.findAll().size();
        // set the field null
        ecartStock.setQuantiteTheorique(null);

        // Create the EcartStock, which fails.
        EcartStockDTO ecartStockDTO = ecartStockMapper.toDto(ecartStock);


        restEcartStockMockMvc.perform(post("/api/ecart-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecartStockDTO)))
            .andExpect(status().isBadRequest());

        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantitePhysiqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecartStockRepository.findAll().size();
        // set the field null
        ecartStock.setQuantitePhysique(null);

        // Create the EcartStock, which fails.
        EcartStockDTO ecartStockDTO = ecartStockMapper.toDto(ecartStock);


        restEcartStockMockMvc.perform(post("/api/ecart-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecartStockDTO)))
            .andExpect(status().isBadRequest());

        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEcartIsRequired() throws Exception {
        int databaseSizeBeforeTest = ecartStockRepository.findAll().size();
        // set the field null
        ecartStock.setEcart(null);

        // Create the EcartStock, which fails.
        EcartStockDTO ecartStockDTO = ecartStockMapper.toDto(ecartStock);


        restEcartStockMockMvc.perform(post("/api/ecart-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecartStockDTO)))
            .andExpect(status().isBadRequest());

        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEcartStocks() throws Exception {
        // Initialize the database
        ecartStockRepository.saveAndFlush(ecartStock);

        // Get all the ecartStockList
        restEcartStockMockMvc.perform(get("/api/ecart-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecartStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantiteTheorique").value(hasItem(DEFAULT_QUANTITE_THEORIQUE)))
            .andExpect(jsonPath("$.[*].quantitePhysique").value(hasItem(DEFAULT_QUANTITE_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].ecart").value(hasItem(DEFAULT_ECART)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getEcartStock() throws Exception {
        // Initialize the database
        ecartStockRepository.saveAndFlush(ecartStock);

        // Get the ecartStock
        restEcartStockMockMvc.perform(get("/api/ecart-stocks/{id}", ecartStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecartStock.getId().intValue()))
            .andExpect(jsonPath("$.quantiteTheorique").value(DEFAULT_QUANTITE_THEORIQUE))
            .andExpect(jsonPath("$.quantitePhysique").value(DEFAULT_QUANTITE_PHYSIQUE))
            .andExpect(jsonPath("$.ecart").value(DEFAULT_ECART))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }
    @Test
    @Transactional
    public void getNonExistingEcartStock() throws Exception {
        // Get the ecartStock
        restEcartStockMockMvc.perform(get("/api/ecart-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEcartStock() throws Exception {
        // Initialize the database
        ecartStockRepository.saveAndFlush(ecartStock);

        int databaseSizeBeforeUpdate = ecartStockRepository.findAll().size();

        // Update the ecartStock
        EcartStock updatedEcartStock = ecartStockRepository.findById(ecartStock.getId()).get();
        // Disconnect from session so that the updates on updatedEcartStock are not directly saved in db
        em.detach(updatedEcartStock);
        updatedEcartStock
            .quantiteTheorique(UPDATED_QUANTITE_THEORIQUE)
            .quantitePhysique(UPDATED_QUANTITE_PHYSIQUE)
            .ecart(UPDATED_ECART)
            .commentaire(UPDATED_COMMENTAIRE);
        EcartStockDTO ecartStockDTO = ecartStockMapper.toDto(updatedEcartStock);

        restEcartStockMockMvc.perform(put("/api/ecart-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecartStockDTO)))
            .andExpect(status().isOk());

        // Validate the EcartStock in the database
        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeUpdate);
        EcartStock testEcartStock = ecartStockList.get(ecartStockList.size() - 1);
        assertThat(testEcartStock.getQuantiteTheorique()).isEqualTo(UPDATED_QUANTITE_THEORIQUE);
        assertThat(testEcartStock.getQuantitePhysique()).isEqualTo(UPDATED_QUANTITE_PHYSIQUE);
        assertThat(testEcartStock.getEcart()).isEqualTo(UPDATED_ECART);
        assertThat(testEcartStock.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingEcartStock() throws Exception {
        int databaseSizeBeforeUpdate = ecartStockRepository.findAll().size();

        // Create the EcartStock
        EcartStockDTO ecartStockDTO = ecartStockMapper.toDto(ecartStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcartStockMockMvc.perform(put("/api/ecart-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecartStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EcartStock in the database
        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEcartStock() throws Exception {
        // Initialize the database
        ecartStockRepository.saveAndFlush(ecartStock);

        int databaseSizeBeforeDelete = ecartStockRepository.findAll().size();

        // Delete the ecartStock
        restEcartStockMockMvc.perform(delete("/api/ecart-stocks/{id}", ecartStock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EcartStock> ecartStockList = ecartStockRepository.findAll();
        assertThat(ecartStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
