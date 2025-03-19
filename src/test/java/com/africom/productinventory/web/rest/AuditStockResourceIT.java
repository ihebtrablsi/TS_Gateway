package com.africom.productinventory.web.rest;

import com.africom.productinventory.ProductInventoryApp;
import com.africom.productinventory.domain.AuditStock;
import com.africom.productinventory.repository.AuditStockRepository;
import com.africom.productinventory.service.AuditStockService;
import com.africom.productinventory.service.dto.AuditStockDTO;
import com.africom.productinventory.service.mapper.AuditStockMapper;

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
 * Integration tests for the {@link AuditStockResource} REST controller.
 */
@SpringBootTest(classes = ProductInventoryApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AuditStockResourceIT {

    private static final Instant DEFAULT_DATE_AUDIT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_AUDIT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private AuditStockRepository auditStockRepository;

    @Autowired
    private AuditStockMapper auditStockMapper;

    @Autowired
    private AuditStockService auditStockService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditStockMockMvc;

    private AuditStock auditStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditStock createEntity(EntityManager em) {
        AuditStock auditStock = new AuditStock()
            .dateAudit(DEFAULT_DATE_AUDIT)
            .statut(DEFAULT_STATUT)
            .commentaire(DEFAULT_COMMENTAIRE);
        return auditStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditStock createUpdatedEntity(EntityManager em) {
        AuditStock auditStock = new AuditStock()
            .dateAudit(UPDATED_DATE_AUDIT)
            .statut(UPDATED_STATUT)
            .commentaire(UPDATED_COMMENTAIRE);
        return auditStock;
    }

    @BeforeEach
    public void initTest() {
        auditStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditStock() throws Exception {
        int databaseSizeBeforeCreate = auditStockRepository.findAll().size();
        // Create the AuditStock
        AuditStockDTO auditStockDTO = auditStockMapper.toDto(auditStock);
        restAuditStockMockMvc.perform(post("/api/audit-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditStockDTO)))
            .andExpect(status().isCreated());

        // Validate the AuditStock in the database
        List<AuditStock> auditStockList = auditStockRepository.findAll();
        assertThat(auditStockList).hasSize(databaseSizeBeforeCreate + 1);
        AuditStock testAuditStock = auditStockList.get(auditStockList.size() - 1);
        assertThat(testAuditStock.getDateAudit()).isEqualTo(DEFAULT_DATE_AUDIT);
        assertThat(testAuditStock.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testAuditStock.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createAuditStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditStockRepository.findAll().size();

        // Create the AuditStock with an existing ID
        auditStock.setId(1L);
        AuditStockDTO auditStockDTO = auditStockMapper.toDto(auditStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditStockMockMvc.perform(post("/api/audit-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditStock in the database
        List<AuditStock> auditStockList = auditStockRepository.findAll();
        assertThat(auditStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditStockRepository.findAll().size();
        // set the field null
        auditStock.setDateAudit(null);

        // Create the AuditStock, which fails.
        AuditStockDTO auditStockDTO = auditStockMapper.toDto(auditStock);


        restAuditStockMockMvc.perform(post("/api/audit-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditStockDTO)))
            .andExpect(status().isBadRequest());

        List<AuditStock> auditStockList = auditStockRepository.findAll();
        assertThat(auditStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditStockRepository.findAll().size();
        // set the field null
        auditStock.setStatut(null);

        // Create the AuditStock, which fails.
        AuditStockDTO auditStockDTO = auditStockMapper.toDto(auditStock);


        restAuditStockMockMvc.perform(post("/api/audit-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditStockDTO)))
            .andExpect(status().isBadRequest());

        List<AuditStock> auditStockList = auditStockRepository.findAll();
        assertThat(auditStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAuditStocks() throws Exception {
        // Initialize the database
        auditStockRepository.saveAndFlush(auditStock);

        // Get all the auditStockList
        restAuditStockMockMvc.perform(get("/api/audit-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAudit").value(hasItem(DEFAULT_DATE_AUDIT.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)));
    }
    
    @Test
    @Transactional
    public void getAuditStock() throws Exception {
        // Initialize the database
        auditStockRepository.saveAndFlush(auditStock);

        // Get the auditStock
        restAuditStockMockMvc.perform(get("/api/audit-stocks/{id}", auditStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditStock.getId().intValue()))
            .andExpect(jsonPath("$.dateAudit").value(DEFAULT_DATE_AUDIT.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE));
    }
    @Test
    @Transactional
    public void getNonExistingAuditStock() throws Exception {
        // Get the auditStock
        restAuditStockMockMvc.perform(get("/api/audit-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditStock() throws Exception {
        // Initialize the database
        auditStockRepository.saveAndFlush(auditStock);

        int databaseSizeBeforeUpdate = auditStockRepository.findAll().size();

        // Update the auditStock
        AuditStock updatedAuditStock = auditStockRepository.findById(auditStock.getId()).get();
        // Disconnect from session so that the updates on updatedAuditStock are not directly saved in db
        em.detach(updatedAuditStock);
        updatedAuditStock
            .dateAudit(UPDATED_DATE_AUDIT)
            .statut(UPDATED_STATUT)
            .commentaire(UPDATED_COMMENTAIRE);
        AuditStockDTO auditStockDTO = auditStockMapper.toDto(updatedAuditStock);

        restAuditStockMockMvc.perform(put("/api/audit-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditStockDTO)))
            .andExpect(status().isOk());

        // Validate the AuditStock in the database
        List<AuditStock> auditStockList = auditStockRepository.findAll();
        assertThat(auditStockList).hasSize(databaseSizeBeforeUpdate);
        AuditStock testAuditStock = auditStockList.get(auditStockList.size() - 1);
        assertThat(testAuditStock.getDateAudit()).isEqualTo(UPDATED_DATE_AUDIT);
        assertThat(testAuditStock.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testAuditStock.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditStock() throws Exception {
        int databaseSizeBeforeUpdate = auditStockRepository.findAll().size();

        // Create the AuditStock
        AuditStockDTO auditStockDTO = auditStockMapper.toDto(auditStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditStockMockMvc.perform(put("/api/audit-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditStock in the database
        List<AuditStock> auditStockList = auditStockRepository.findAll();
        assertThat(auditStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditStock() throws Exception {
        // Initialize the database
        auditStockRepository.saveAndFlush(auditStock);

        int databaseSizeBeforeDelete = auditStockRepository.findAll().size();

        // Delete the auditStock
        restAuditStockMockMvc.perform(delete("/api/audit-stocks/{id}", auditStock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditStock> auditStockList = auditStockRepository.findAll();
        assertThat(auditStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
