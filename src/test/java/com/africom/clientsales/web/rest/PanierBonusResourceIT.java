package com.africom.clientsales.web.rest;

import com.africom.clientsales.ClientSalesApp;
import com.africom.clientsales.domain.PanierBonus;
import com.africom.clientsales.repository.PanierBonusRepository;
import com.africom.clientsales.service.PanierBonusService;
import com.africom.clientsales.service.dto.PanierBonusDTO;
import com.africom.clientsales.service.mapper.PanierBonusMapper;

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
 * Integration tests for the {@link PanierBonusResource} REST controller.
 */
@SpringBootTest(classes = ClientSalesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PanierBonusResourceIT {

    private static final Integer DEFAULT_TOTAL_POINTS = 0;
    private static final Integer UPDATED_TOTAL_POINTS = 1;

    private static final Double DEFAULT_TOTAL_VALEUR = 0D;
    private static final Double UPDATED_TOTAL_VALEUR = 1D;

    @Autowired
    private PanierBonusRepository panierBonusRepository;

    @Autowired
    private PanierBonusMapper panierBonusMapper;

    @Autowired
    private PanierBonusService panierBonusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPanierBonusMockMvc;

    private PanierBonus panierBonus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PanierBonus createEntity(EntityManager em) {
        PanierBonus panierBonus = new PanierBonus()
            .totalPoints(DEFAULT_TOTAL_POINTS)
            .totalValeur(DEFAULT_TOTAL_VALEUR);
        return panierBonus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PanierBonus createUpdatedEntity(EntityManager em) {
        PanierBonus panierBonus = new PanierBonus()
            .totalPoints(UPDATED_TOTAL_POINTS)
            .totalValeur(UPDATED_TOTAL_VALEUR);
        return panierBonus;
    }

    @BeforeEach
    public void initTest() {
        panierBonus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPanierBonus() throws Exception {
        int databaseSizeBeforeCreate = panierBonusRepository.findAll().size();
        // Create the PanierBonus
        PanierBonusDTO panierBonusDTO = panierBonusMapper.toDto(panierBonus);
        restPanierBonusMockMvc.perform(post("/api/panier-bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierBonusDTO)))
            .andExpect(status().isCreated());

        // Validate the PanierBonus in the database
        List<PanierBonus> panierBonusList = panierBonusRepository.findAll();
        assertThat(panierBonusList).hasSize(databaseSizeBeforeCreate + 1);
        PanierBonus testPanierBonus = panierBonusList.get(panierBonusList.size() - 1);
        assertThat(testPanierBonus.getTotalPoints()).isEqualTo(DEFAULT_TOTAL_POINTS);
        assertThat(testPanierBonus.getTotalValeur()).isEqualTo(DEFAULT_TOTAL_VALEUR);
    }

    @Test
    @Transactional
    public void createPanierBonusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = panierBonusRepository.findAll().size();

        // Create the PanierBonus with an existing ID
        panierBonus.setId(1L);
        PanierBonusDTO panierBonusDTO = panierBonusMapper.toDto(panierBonus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPanierBonusMockMvc.perform(post("/api/panier-bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierBonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PanierBonus in the database
        List<PanierBonus> panierBonusList = panierBonusRepository.findAll();
        assertThat(panierBonusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = panierBonusRepository.findAll().size();
        // set the field null
        panierBonus.setTotalPoints(null);

        // Create the PanierBonus, which fails.
        PanierBonusDTO panierBonusDTO = panierBonusMapper.toDto(panierBonus);


        restPanierBonusMockMvc.perform(post("/api/panier-bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierBonusDTO)))
            .andExpect(status().isBadRequest());

        List<PanierBonus> panierBonusList = panierBonusRepository.findAll();
        assertThat(panierBonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = panierBonusRepository.findAll().size();
        // set the field null
        panierBonus.setTotalValeur(null);

        // Create the PanierBonus, which fails.
        PanierBonusDTO panierBonusDTO = panierBonusMapper.toDto(panierBonus);


        restPanierBonusMockMvc.perform(post("/api/panier-bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierBonusDTO)))
            .andExpect(status().isBadRequest());

        List<PanierBonus> panierBonusList = panierBonusRepository.findAll();
        assertThat(panierBonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPanierBonuses() throws Exception {
        // Initialize the database
        panierBonusRepository.saveAndFlush(panierBonus);

        // Get all the panierBonusList
        restPanierBonusMockMvc.perform(get("/api/panier-bonuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panierBonus.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS)))
            .andExpect(jsonPath("$.[*].totalValeur").value(hasItem(DEFAULT_TOTAL_VALEUR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPanierBonus() throws Exception {
        // Initialize the database
        panierBonusRepository.saveAndFlush(panierBonus);

        // Get the panierBonus
        restPanierBonusMockMvc.perform(get("/api/panier-bonuses/{id}", panierBonus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(panierBonus.getId().intValue()))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS))
            .andExpect(jsonPath("$.totalValeur").value(DEFAULT_TOTAL_VALEUR.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPanierBonus() throws Exception {
        // Get the panierBonus
        restPanierBonusMockMvc.perform(get("/api/panier-bonuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePanierBonus() throws Exception {
        // Initialize the database
        panierBonusRepository.saveAndFlush(panierBonus);

        int databaseSizeBeforeUpdate = panierBonusRepository.findAll().size();

        // Update the panierBonus
        PanierBonus updatedPanierBonus = panierBonusRepository.findById(panierBonus.getId()).get();
        // Disconnect from session so that the updates on updatedPanierBonus are not directly saved in db
        em.detach(updatedPanierBonus);
        updatedPanierBonus
            .totalPoints(UPDATED_TOTAL_POINTS)
            .totalValeur(UPDATED_TOTAL_VALEUR);
        PanierBonusDTO panierBonusDTO = panierBonusMapper.toDto(updatedPanierBonus);

        restPanierBonusMockMvc.perform(put("/api/panier-bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierBonusDTO)))
            .andExpect(status().isOk());

        // Validate the PanierBonus in the database
        List<PanierBonus> panierBonusList = panierBonusRepository.findAll();
        assertThat(panierBonusList).hasSize(databaseSizeBeforeUpdate);
        PanierBonus testPanierBonus = panierBonusList.get(panierBonusList.size() - 1);
        assertThat(testPanierBonus.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testPanierBonus.getTotalValeur()).isEqualTo(UPDATED_TOTAL_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingPanierBonus() throws Exception {
        int databaseSizeBeforeUpdate = panierBonusRepository.findAll().size();

        // Create the PanierBonus
        PanierBonusDTO panierBonusDTO = panierBonusMapper.toDto(panierBonus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPanierBonusMockMvc.perform(put("/api/panier-bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(panierBonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PanierBonus in the database
        List<PanierBonus> panierBonusList = panierBonusRepository.findAll();
        assertThat(panierBonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePanierBonus() throws Exception {
        // Initialize the database
        panierBonusRepository.saveAndFlush(panierBonus);

        int databaseSizeBeforeDelete = panierBonusRepository.findAll().size();

        // Delete the panierBonus
        restPanierBonusMockMvc.perform(delete("/api/panier-bonuses/{id}", panierBonus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PanierBonus> panierBonusList = panierBonusRepository.findAll();
        assertThat(panierBonusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
