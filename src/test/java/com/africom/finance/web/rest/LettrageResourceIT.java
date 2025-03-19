package com.africom.finance.web.rest;

import com.africom.finance.FinanceApp;
import com.africom.finance.domain.Lettrage;
import com.africom.finance.repository.LettrageRepository;
import com.africom.finance.service.LettrageService;
import com.africom.finance.service.dto.LettrageDTO;
import com.africom.finance.service.mapper.LettrageMapper;

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
 * Integration tests for the {@link LettrageResource} REST controller.
 */
@SpringBootTest(classes = FinanceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LettrageResourceIT {

    private static final Instant DEFAULT_DATE_LETTRAGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_LETTRAGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_MONTANT_LETTRE = 0D;
    private static final Double UPDATED_MONTANT_LETTRE = 1D;

    @Autowired
    private LettrageRepository lettrageRepository;

    @Autowired
    private LettrageMapper lettrageMapper;

    @Autowired
    private LettrageService lettrageService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLettrageMockMvc;

    private Lettrage lettrage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lettrage createEntity(EntityManager em) {
        Lettrage lettrage = new Lettrage()
            .dateLettrage(DEFAULT_DATE_LETTRAGE)
            .montantLettre(DEFAULT_MONTANT_LETTRE);
        return lettrage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lettrage createUpdatedEntity(EntityManager em) {
        Lettrage lettrage = new Lettrage()
            .dateLettrage(UPDATED_DATE_LETTRAGE)
            .montantLettre(UPDATED_MONTANT_LETTRE);
        return lettrage;
    }

    @BeforeEach
    public void initTest() {
        lettrage = createEntity(em);
    }

    @Test
    @Transactional
    public void createLettrage() throws Exception {
        int databaseSizeBeforeCreate = lettrageRepository.findAll().size();
        // Create the Lettrage
        LettrageDTO lettrageDTO = lettrageMapper.toDto(lettrage);
        restLettrageMockMvc.perform(post("/api/lettrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lettrageDTO)))
            .andExpect(status().isCreated());

        // Validate the Lettrage in the database
        List<Lettrage> lettrageList = lettrageRepository.findAll();
        assertThat(lettrageList).hasSize(databaseSizeBeforeCreate + 1);
        Lettrage testLettrage = lettrageList.get(lettrageList.size() - 1);
        assertThat(testLettrage.getDateLettrage()).isEqualTo(DEFAULT_DATE_LETTRAGE);
        assertThat(testLettrage.getMontantLettre()).isEqualTo(DEFAULT_MONTANT_LETTRE);
    }

    @Test
    @Transactional
    public void createLettrageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lettrageRepository.findAll().size();

        // Create the Lettrage with an existing ID
        lettrage.setId(1L);
        LettrageDTO lettrageDTO = lettrageMapper.toDto(lettrage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLettrageMockMvc.perform(post("/api/lettrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lettrageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lettrage in the database
        List<Lettrage> lettrageList = lettrageRepository.findAll();
        assertThat(lettrageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateLettrageIsRequired() throws Exception {
        int databaseSizeBeforeTest = lettrageRepository.findAll().size();
        // set the field null
        lettrage.setDateLettrage(null);

        // Create the Lettrage, which fails.
        LettrageDTO lettrageDTO = lettrageMapper.toDto(lettrage);


        restLettrageMockMvc.perform(post("/api/lettrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lettrageDTO)))
            .andExpect(status().isBadRequest());

        List<Lettrage> lettrageList = lettrageRepository.findAll();
        assertThat(lettrageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantLettreIsRequired() throws Exception {
        int databaseSizeBeforeTest = lettrageRepository.findAll().size();
        // set the field null
        lettrage.setMontantLettre(null);

        // Create the Lettrage, which fails.
        LettrageDTO lettrageDTO = lettrageMapper.toDto(lettrage);


        restLettrageMockMvc.perform(post("/api/lettrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lettrageDTO)))
            .andExpect(status().isBadRequest());

        List<Lettrage> lettrageList = lettrageRepository.findAll();
        assertThat(lettrageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLettrages() throws Exception {
        // Initialize the database
        lettrageRepository.saveAndFlush(lettrage);

        // Get all the lettrageList
        restLettrageMockMvc.perform(get("/api/lettrages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lettrage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateLettrage").value(hasItem(DEFAULT_DATE_LETTRAGE.toString())))
            .andExpect(jsonPath("$.[*].montantLettre").value(hasItem(DEFAULT_MONTANT_LETTRE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLettrage() throws Exception {
        // Initialize the database
        lettrageRepository.saveAndFlush(lettrage);

        // Get the lettrage
        restLettrageMockMvc.perform(get("/api/lettrages/{id}", lettrage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lettrage.getId().intValue()))
            .andExpect(jsonPath("$.dateLettrage").value(DEFAULT_DATE_LETTRAGE.toString()))
            .andExpect(jsonPath("$.montantLettre").value(DEFAULT_MONTANT_LETTRE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLettrage() throws Exception {
        // Get the lettrage
        restLettrageMockMvc.perform(get("/api/lettrages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLettrage() throws Exception {
        // Initialize the database
        lettrageRepository.saveAndFlush(lettrage);

        int databaseSizeBeforeUpdate = lettrageRepository.findAll().size();

        // Update the lettrage
        Lettrage updatedLettrage = lettrageRepository.findById(lettrage.getId()).get();
        // Disconnect from session so that the updates on updatedLettrage are not directly saved in db
        em.detach(updatedLettrage);
        updatedLettrage
            .dateLettrage(UPDATED_DATE_LETTRAGE)
            .montantLettre(UPDATED_MONTANT_LETTRE);
        LettrageDTO lettrageDTO = lettrageMapper.toDto(updatedLettrage);

        restLettrageMockMvc.perform(put("/api/lettrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lettrageDTO)))
            .andExpect(status().isOk());

        // Validate the Lettrage in the database
        List<Lettrage> lettrageList = lettrageRepository.findAll();
        assertThat(lettrageList).hasSize(databaseSizeBeforeUpdate);
        Lettrage testLettrage = lettrageList.get(lettrageList.size() - 1);
        assertThat(testLettrage.getDateLettrage()).isEqualTo(UPDATED_DATE_LETTRAGE);
        assertThat(testLettrage.getMontantLettre()).isEqualTo(UPDATED_MONTANT_LETTRE);
    }

    @Test
    @Transactional
    public void updateNonExistingLettrage() throws Exception {
        int databaseSizeBeforeUpdate = lettrageRepository.findAll().size();

        // Create the Lettrage
        LettrageDTO lettrageDTO = lettrageMapper.toDto(lettrage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLettrageMockMvc.perform(put("/api/lettrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lettrageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lettrage in the database
        List<Lettrage> lettrageList = lettrageRepository.findAll();
        assertThat(lettrageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLettrage() throws Exception {
        // Initialize the database
        lettrageRepository.saveAndFlush(lettrage);

        int databaseSizeBeforeDelete = lettrageRepository.findAll().size();

        // Delete the lettrage
        restLettrageMockMvc.perform(delete("/api/lettrages/{id}", lettrage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lettrage> lettrageList = lettrageRepository.findAll();
        assertThat(lettrageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
