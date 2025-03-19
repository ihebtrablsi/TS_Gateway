package com.africom.clientsales.web.rest;

import com.africom.clientsales.ClientSalesApp;
import com.africom.clientsales.domain.PointDeVente;
import com.africom.clientsales.repository.PointDeVenteRepository;
import com.africom.clientsales.service.PointDeVenteService;
import com.africom.clientsales.service.dto.PointDeVenteDTO;
import com.africom.clientsales.service.mapper.PointDeVenteMapper;

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
 * Integration tests for the {@link PointDeVenteResource} REST controller.
 */
@SpringBootTest(classes = ClientSalesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PointDeVenteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Autowired
    private PointDeVenteRepository pointDeVenteRepository;

    @Autowired
    private PointDeVenteMapper pointDeVenteMapper;

    @Autowired
    private PointDeVenteService pointDeVenteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointDeVenteMockMvc;

    private PointDeVente pointDeVente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVente createEntity(EntityManager em) {
        PointDeVente pointDeVente = new PointDeVente()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .ville(DEFAULT_VILLE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .telephone(DEFAULT_TELEPHONE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return pointDeVente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointDeVente createUpdatedEntity(EntityManager em) {
        PointDeVente pointDeVente = new PointDeVente()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .codePostal(UPDATED_CODE_POSTAL)
            .telephone(UPDATED_TELEPHONE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return pointDeVente;
    }

    @BeforeEach
    public void initTest() {
        pointDeVente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPointDeVente() throws Exception {
        int databaseSizeBeforeCreate = pointDeVenteRepository.findAll().size();
        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);
        restPointDeVenteMockMvc.perform(post("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isCreated());

        // Validate the PointDeVente in the database
        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeCreate + 1);
        PointDeVente testPointDeVente = pointDeVenteList.get(pointDeVenteList.size() - 1);
        assertThat(testPointDeVente.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPointDeVente.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPointDeVente.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testPointDeVente.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testPointDeVente.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testPointDeVente.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPointDeVente.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createPointDeVenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pointDeVenteRepository.findAll().size();

        // Create the PointDeVente with an existing ID
        pointDeVente.setId(1L);
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointDeVenteMockMvc.perform(post("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PointDeVente in the database
        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointDeVenteRepository.findAll().size();
        // set the field null
        pointDeVente.setNom(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);


        restPointDeVenteMockMvc.perform(post("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointDeVenteRepository.findAll().size();
        // set the field null
        pointDeVente.setAdresse(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);


        restPointDeVenteMockMvc.perform(post("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointDeVenteRepository.findAll().size();
        // set the field null
        pointDeVente.setVille(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);


        restPointDeVenteMockMvc.perform(post("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointDeVenteRepository.findAll().size();
        // set the field null
        pointDeVente.setLatitude(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);


        restPointDeVenteMockMvc.perform(post("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointDeVenteRepository.findAll().size();
        // set the field null
        pointDeVente.setLongitude(null);

        // Create the PointDeVente, which fails.
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);


        restPointDeVenteMockMvc.perform(post("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPointDeVentes() throws Exception {
        // Initialize the database
        pointDeVenteRepository.saveAndFlush(pointDeVente);

        // Get all the pointDeVenteList
        restPointDeVenteMockMvc.perform(get("/api/point-de-ventes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointDeVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPointDeVente() throws Exception {
        // Initialize the database
        pointDeVenteRepository.saveAndFlush(pointDeVente);

        // Get the pointDeVente
        restPointDeVenteMockMvc.perform(get("/api/point-de-ventes/{id}", pointDeVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointDeVente.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPointDeVente() throws Exception {
        // Get the pointDeVente
        restPointDeVenteMockMvc.perform(get("/api/point-de-ventes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePointDeVente() throws Exception {
        // Initialize the database
        pointDeVenteRepository.saveAndFlush(pointDeVente);

        int databaseSizeBeforeUpdate = pointDeVenteRepository.findAll().size();

        // Update the pointDeVente
        PointDeVente updatedPointDeVente = pointDeVenteRepository.findById(pointDeVente.getId()).get();
        // Disconnect from session so that the updates on updatedPointDeVente are not directly saved in db
        em.detach(updatedPointDeVente);
        updatedPointDeVente
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .codePostal(UPDATED_CODE_POSTAL)
            .telephone(UPDATED_TELEPHONE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(updatedPointDeVente);

        restPointDeVenteMockMvc.perform(put("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isOk());

        // Validate the PointDeVente in the database
        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeUpdate);
        PointDeVente testPointDeVente = pointDeVenteList.get(pointDeVenteList.size() - 1);
        assertThat(testPointDeVente.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPointDeVente.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPointDeVente.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testPointDeVente.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testPointDeVente.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testPointDeVente.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPointDeVente.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingPointDeVente() throws Exception {
        int databaseSizeBeforeUpdate = pointDeVenteRepository.findAll().size();

        // Create the PointDeVente
        PointDeVenteDTO pointDeVenteDTO = pointDeVenteMapper.toDto(pointDeVente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointDeVenteMockMvc.perform(put("/api/point-de-ventes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pointDeVenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PointDeVente in the database
        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePointDeVente() throws Exception {
        // Initialize the database
        pointDeVenteRepository.saveAndFlush(pointDeVente);

        int databaseSizeBeforeDelete = pointDeVenteRepository.findAll().size();

        // Delete the pointDeVente
        restPointDeVenteMockMvc.perform(delete("/api/point-de-ventes/{id}", pointDeVente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointDeVente> pointDeVenteList = pointDeVenteRepository.findAll();
        assertThat(pointDeVenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
