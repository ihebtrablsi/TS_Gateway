package com.africom.finance.web.rest;

import com.africom.finance.FinanceApp;
import com.africom.finance.domain.Incentive;
import com.africom.finance.repository.IncentiveRepository;
import com.africom.finance.service.IncentiveService;
import com.africom.finance.service.dto.IncentiveDTO;
import com.africom.finance.service.mapper.IncentiveMapper;

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
 * Integration tests for the {@link IncentiveResource} REST controller.
 */
@SpringBootTest(classes = FinanceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IncentiveResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REGLES = "AAAAAAAAAA";
    private static final String UPDATED_REGLES = "BBBBBBBBBB";

    private static final String DEFAULT_BONUS = "AAAAAAAAAA";
    private static final String UPDATED_BONUS = "BBBBBBBBBB";

    @Autowired
    private IncentiveRepository incentiveRepository;

    @Autowired
    private IncentiveMapper incentiveMapper;

    @Autowired
    private IncentiveService incentiveService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncentiveMockMvc;

    private Incentive incentive;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incentive createEntity(EntityManager em) {
        Incentive incentive = new Incentive()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .regles(DEFAULT_REGLES)
            .bonus(DEFAULT_BONUS);
        return incentive;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incentive createUpdatedEntity(EntityManager em) {
        Incentive incentive = new Incentive()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .regles(UPDATED_REGLES)
            .bonus(UPDATED_BONUS);
        return incentive;
    }

    @BeforeEach
    public void initTest() {
        incentive = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncentive() throws Exception {
        int databaseSizeBeforeCreate = incentiveRepository.findAll().size();
        // Create the Incentive
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);
        restIncentiveMockMvc.perform(post("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isCreated());

        // Validate the Incentive in the database
        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeCreate + 1);
        Incentive testIncentive = incentiveList.get(incentiveList.size() - 1);
        assertThat(testIncentive.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testIncentive.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIncentive.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testIncentive.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testIncentive.getRegles()).isEqualTo(DEFAULT_REGLES);
        assertThat(testIncentive.getBonus()).isEqualTo(DEFAULT_BONUS);
    }

    @Test
    @Transactional
    public void createIncentiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incentiveRepository.findAll().size();

        // Create the Incentive with an existing ID
        incentive.setId(1L);
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncentiveMockMvc.perform(post("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incentive in the database
        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = incentiveRepository.findAll().size();
        // set the field null
        incentive.setNom(null);

        // Create the Incentive, which fails.
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);


        restIncentiveMockMvc.perform(post("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isBadRequest());

        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = incentiveRepository.findAll().size();
        // set the field null
        incentive.setDateDebut(null);

        // Create the Incentive, which fails.
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);


        restIncentiveMockMvc.perform(post("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isBadRequest());

        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = incentiveRepository.findAll().size();
        // set the field null
        incentive.setDateFin(null);

        // Create the Incentive, which fails.
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);


        restIncentiveMockMvc.perform(post("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isBadRequest());

        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReglesIsRequired() throws Exception {
        int databaseSizeBeforeTest = incentiveRepository.findAll().size();
        // set the field null
        incentive.setRegles(null);

        // Create the Incentive, which fails.
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);


        restIncentiveMockMvc.perform(post("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isBadRequest());

        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBonusIsRequired() throws Exception {
        int databaseSizeBeforeTest = incentiveRepository.findAll().size();
        // set the field null
        incentive.setBonus(null);

        // Create the Incentive, which fails.
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);


        restIncentiveMockMvc.perform(post("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isBadRequest());

        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIncentives() throws Exception {
        // Initialize the database
        incentiveRepository.saveAndFlush(incentive);

        // Get all the incentiveList
        restIncentiveMockMvc.perform(get("/api/incentives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incentive.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].regles").value(hasItem(DEFAULT_REGLES)))
            .andExpect(jsonPath("$.[*].bonus").value(hasItem(DEFAULT_BONUS)));
    }
    
    @Test
    @Transactional
    public void getIncentive() throws Exception {
        // Initialize the database
        incentiveRepository.saveAndFlush(incentive);

        // Get the incentive
        restIncentiveMockMvc.perform(get("/api/incentives/{id}", incentive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incentive.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.regles").value(DEFAULT_REGLES))
            .andExpect(jsonPath("$.bonus").value(DEFAULT_BONUS));
    }
    @Test
    @Transactional
    public void getNonExistingIncentive() throws Exception {
        // Get the incentive
        restIncentiveMockMvc.perform(get("/api/incentives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncentive() throws Exception {
        // Initialize the database
        incentiveRepository.saveAndFlush(incentive);

        int databaseSizeBeforeUpdate = incentiveRepository.findAll().size();

        // Update the incentive
        Incentive updatedIncentive = incentiveRepository.findById(incentive.getId()).get();
        // Disconnect from session so that the updates on updatedIncentive are not directly saved in db
        em.detach(updatedIncentive);
        updatedIncentive
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .regles(UPDATED_REGLES)
            .bonus(UPDATED_BONUS);
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(updatedIncentive);

        restIncentiveMockMvc.perform(put("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isOk());

        // Validate the Incentive in the database
        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeUpdate);
        Incentive testIncentive = incentiveList.get(incentiveList.size() - 1);
        assertThat(testIncentive.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testIncentive.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIncentive.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testIncentive.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testIncentive.getRegles()).isEqualTo(UPDATED_REGLES);
        assertThat(testIncentive.getBonus()).isEqualTo(UPDATED_BONUS);
    }

    @Test
    @Transactional
    public void updateNonExistingIncentive() throws Exception {
        int databaseSizeBeforeUpdate = incentiveRepository.findAll().size();

        // Create the Incentive
        IncentiveDTO incentiveDTO = incentiveMapper.toDto(incentive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncentiveMockMvc.perform(put("/api/incentives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incentiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Incentive in the database
        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncentive() throws Exception {
        // Initialize the database
        incentiveRepository.saveAndFlush(incentive);

        int databaseSizeBeforeDelete = incentiveRepository.findAll().size();

        // Delete the incentive
        restIncentiveMockMvc.perform(delete("/api/incentives/{id}", incentive.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Incentive> incentiveList = incentiveRepository.findAll();
        assertThat(incentiveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
