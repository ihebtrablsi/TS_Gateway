package com.africom.finance.web.rest;

import com.africom.finance.FinanceApp;
import com.africom.finance.domain.Ristourne;
import com.africom.finance.repository.RistourneRepository;
import com.africom.finance.service.RistourneService;
import com.africom.finance.service.dto.RistourneDTO;
import com.africom.finance.service.mapper.RistourneMapper;

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
 * Integration tests for the {@link RistourneResource} REST controller.
 */
@SpringBootTest(classes = FinanceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RistourneResourceIT {

    private static final Double DEFAULT_MONTANT = 0D;
    private static final Double UPDATED_MONTANT = 1D;

    private static final Instant DEFAULT_DATE_ATTRIBUTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ATTRIBUTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RistourneRepository ristourneRepository;

    @Autowired
    private RistourneMapper ristourneMapper;

    @Autowired
    private RistourneService ristourneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRistourneMockMvc;

    private Ristourne ristourne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ristourne createEntity(EntityManager em) {
        Ristourne ristourne = new Ristourne()
            .montant(DEFAULT_MONTANT)
            .dateAttribution(DEFAULT_DATE_ATTRIBUTION);
        return ristourne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ristourne createUpdatedEntity(EntityManager em) {
        Ristourne ristourne = new Ristourne()
            .montant(UPDATED_MONTANT)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION);
        return ristourne;
    }

    @BeforeEach
    public void initTest() {
        ristourne = createEntity(em);
    }

    @Test
    @Transactional
    public void createRistourne() throws Exception {
        int databaseSizeBeforeCreate = ristourneRepository.findAll().size();
        // Create the Ristourne
        RistourneDTO ristourneDTO = ristourneMapper.toDto(ristourne);
        restRistourneMockMvc.perform(post("/api/ristournes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ristourneDTO)))
            .andExpect(status().isCreated());

        // Validate the Ristourne in the database
        List<Ristourne> ristourneList = ristourneRepository.findAll();
        assertThat(ristourneList).hasSize(databaseSizeBeforeCreate + 1);
        Ristourne testRistourne = ristourneList.get(ristourneList.size() - 1);
        assertThat(testRistourne.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testRistourne.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
    }

    @Test
    @Transactional
    public void createRistourneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ristourneRepository.findAll().size();

        // Create the Ristourne with an existing ID
        ristourne.setId(1L);
        RistourneDTO ristourneDTO = ristourneMapper.toDto(ristourne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRistourneMockMvc.perform(post("/api/ristournes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ristourneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ristourne in the database
        List<Ristourne> ristourneList = ristourneRepository.findAll();
        assertThat(ristourneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = ristourneRepository.findAll().size();
        // set the field null
        ristourne.setMontant(null);

        // Create the Ristourne, which fails.
        RistourneDTO ristourneDTO = ristourneMapper.toDto(ristourne);


        restRistourneMockMvc.perform(post("/api/ristournes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ristourneDTO)))
            .andExpect(status().isBadRequest());

        List<Ristourne> ristourneList = ristourneRepository.findAll();
        assertThat(ristourneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAttributionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ristourneRepository.findAll().size();
        // set the field null
        ristourne.setDateAttribution(null);

        // Create the Ristourne, which fails.
        RistourneDTO ristourneDTO = ristourneMapper.toDto(ristourne);


        restRistourneMockMvc.perform(post("/api/ristournes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ristourneDTO)))
            .andExpect(status().isBadRequest());

        List<Ristourne> ristourneList = ristourneRepository.findAll();
        assertThat(ristourneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRistournes() throws Exception {
        // Initialize the database
        ristourneRepository.saveAndFlush(ristourne);

        // Get all the ristourneList
        restRistourneMockMvc.perform(get("/api/ristournes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ristourne.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateAttribution").value(hasItem(DEFAULT_DATE_ATTRIBUTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRistourne() throws Exception {
        // Initialize the database
        ristourneRepository.saveAndFlush(ristourne);

        // Get the ristourne
        restRistourneMockMvc.perform(get("/api/ristournes/{id}", ristourne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ristourne.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.dateAttribution").value(DEFAULT_DATE_ATTRIBUTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRistourne() throws Exception {
        // Get the ristourne
        restRistourneMockMvc.perform(get("/api/ristournes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRistourne() throws Exception {
        // Initialize the database
        ristourneRepository.saveAndFlush(ristourne);

        int databaseSizeBeforeUpdate = ristourneRepository.findAll().size();

        // Update the ristourne
        Ristourne updatedRistourne = ristourneRepository.findById(ristourne.getId()).get();
        // Disconnect from session so that the updates on updatedRistourne are not directly saved in db
        em.detach(updatedRistourne);
        updatedRistourne
            .montant(UPDATED_MONTANT)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION);
        RistourneDTO ristourneDTO = ristourneMapper.toDto(updatedRistourne);

        restRistourneMockMvc.perform(put("/api/ristournes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ristourneDTO)))
            .andExpect(status().isOk());

        // Validate the Ristourne in the database
        List<Ristourne> ristourneList = ristourneRepository.findAll();
        assertThat(ristourneList).hasSize(databaseSizeBeforeUpdate);
        Ristourne testRistourne = ristourneList.get(ristourneList.size() - 1);
        assertThat(testRistourne.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testRistourne.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRistourne() throws Exception {
        int databaseSizeBeforeUpdate = ristourneRepository.findAll().size();

        // Create the Ristourne
        RistourneDTO ristourneDTO = ristourneMapper.toDto(ristourne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRistourneMockMvc.perform(put("/api/ristournes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ristourneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ristourne in the database
        List<Ristourne> ristourneList = ristourneRepository.findAll();
        assertThat(ristourneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRistourne() throws Exception {
        // Initialize the database
        ristourneRepository.saveAndFlush(ristourne);

        int databaseSizeBeforeDelete = ristourneRepository.findAll().size();

        // Delete the ristourne
        restRistourneMockMvc.perform(delete("/api/ristournes/{id}", ristourne.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ristourne> ristourneList = ristourneRepository.findAll();
        assertThat(ristourneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
