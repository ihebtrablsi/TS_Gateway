package com.africom.finance.web.rest;

import com.africom.finance.FinanceApp;
import com.africom.finance.domain.Bonus;
import com.africom.finance.repository.BonusRepository;
import com.africom.finance.service.BonusService;
import com.africom.finance.service.dto.BonusDTO;
import com.africom.finance.service.mapper.BonusMapper;

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
 * Integration tests for the {@link BonusResource} REST controller.
 */
@SpringBootTest(classes = FinanceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BonusResourceIT {

    private static final Double DEFAULT_MONTANT = 0D;
    private static final Double UPDATED_MONTANT = 1D;

    private static final Instant DEFAULT_DATE_ATTRIBUTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ATTRIBUTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBonusMockMvc;

    private Bonus bonus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonus createEntity(EntityManager em) {
        Bonus bonus = new Bonus()
            .montant(DEFAULT_MONTANT)
            .dateAttribution(DEFAULT_DATE_ATTRIBUTION)
            .statut(DEFAULT_STATUT);
        return bonus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonus createUpdatedEntity(EntityManager em) {
        Bonus bonus = new Bonus()
            .montant(UPDATED_MONTANT)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .statut(UPDATED_STATUT);
        return bonus;
    }

    @BeforeEach
    public void initTest() {
        bonus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonus() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();
        // Create the Bonus
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isCreated());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate + 1);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testBonus.getDateAttribution()).isEqualTo(DEFAULT_DATE_ATTRIBUTION);
        assertThat(testBonus.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createBonusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();

        // Create the Bonus with an existing ID
        bonus.setId(1L);
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setMontant(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);


        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAttributionIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setDateAttribution(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);


        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setStatut(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);


        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBonuses() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList
        restBonusMockMvc.perform(get("/api/bonuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonus.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateAttribution").value(hasItem(DEFAULT_DATE_ATTRIBUTION.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));
    }
    
    @Test
    @Transactional
    public void getBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", bonus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonus.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.dateAttribution").value(DEFAULT_DATE_ATTRIBUTION.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT));
    }
    @Test
    @Transactional
    public void getNonExistingBonus() throws Exception {
        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Update the bonus
        Bonus updatedBonus = bonusRepository.findById(bonus.getId()).get();
        // Disconnect from session so that the updates on updatedBonus are not directly saved in db
        em.detach(updatedBonus);
        updatedBonus
            .montant(UPDATED_MONTANT)
            .dateAttribution(UPDATED_DATE_ATTRIBUTION)
            .statut(UPDATED_STATUT);
        BonusDTO bonusDTO = bonusMapper.toDto(updatedBonus);

        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isOk());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testBonus.getDateAttribution()).isEqualTo(UPDATED_DATE_ATTRIBUTION);
        assertThat(testBonus.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingBonus() throws Exception {
        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Create the Bonus
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        int databaseSizeBeforeDelete = bonusRepository.findAll().size();

        // Delete the bonus
        restBonusMockMvc.perform(delete("/api/bonuses/{id}", bonus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
