package com.africom.clientsales.web.rest;

import com.africom.clientsales.service.PanierBonusService;
import com.africom.clientsales.web.rest.errors.BadRequestAlertException;
import com.africom.clientsales.service.dto.PanierBonusDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.africom.clientsales.domain.PanierBonus}.
 */
@RestController
@RequestMapping("/api")
public class PanierBonusResource {

    private final Logger log = LoggerFactory.getLogger(PanierBonusResource.class);

    private static final String ENTITY_NAME = "clientSalesPanierBonus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PanierBonusService panierBonusService;

    public PanierBonusResource(PanierBonusService panierBonusService) {
        this.panierBonusService = panierBonusService;
    }

    /**
     * {@code POST  /panier-bonuses} : Create a new panierBonus.
     *
     * @param panierBonusDTO the panierBonusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new panierBonusDTO, or with status {@code 400 (Bad Request)} if the panierBonus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/panier-bonuses")
    public ResponseEntity<PanierBonusDTO> createPanierBonus(@Valid @RequestBody PanierBonusDTO panierBonusDTO) throws URISyntaxException {
        log.debug("REST request to save PanierBonus : {}", panierBonusDTO);
        if (panierBonusDTO.getId() != null) {
            throw new BadRequestAlertException("A new panierBonus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PanierBonusDTO result = panierBonusService.save(panierBonusDTO);
        return ResponseEntity.created(new URI("/api/panier-bonuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /panier-bonuses} : Updates an existing panierBonus.
     *
     * @param panierBonusDTO the panierBonusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated panierBonusDTO,
     * or with status {@code 400 (Bad Request)} if the panierBonusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the panierBonusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/panier-bonuses")
    public ResponseEntity<PanierBonusDTO> updatePanierBonus(@Valid @RequestBody PanierBonusDTO panierBonusDTO) throws URISyntaxException {
        log.debug("REST request to update PanierBonus : {}", panierBonusDTO);
        if (panierBonusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PanierBonusDTO result = panierBonusService.save(panierBonusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, panierBonusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /panier-bonuses} : get all the panierBonuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of panierBonuses in body.
     */
    @GetMapping("/panier-bonuses")
    public ResponseEntity<List<PanierBonusDTO>> getAllPanierBonuses(Pageable pageable) {
        log.debug("REST request to get a page of PanierBonuses");
        Page<PanierBonusDTO> page = panierBonusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /panier-bonuses/:id} : get the "id" panierBonus.
     *
     * @param id the id of the panierBonusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the panierBonusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/panier-bonuses/{id}")
    public ResponseEntity<PanierBonusDTO> getPanierBonus(@PathVariable Long id) {
        log.debug("REST request to get PanierBonus : {}", id);
        Optional<PanierBonusDTO> panierBonusDTO = panierBonusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(panierBonusDTO);
    }

    /**
     * {@code DELETE  /panier-bonuses/:id} : delete the "id" panierBonus.
     *
     * @param id the id of the panierBonusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/panier-bonuses/{id}")
    public ResponseEntity<Void> deletePanierBonus(@PathVariable Long id) {
        log.debug("REST request to delete PanierBonus : {}", id);
        panierBonusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
