package com.africom.clientsales.web.rest;

import com.africom.clientsales.service.SuiviActionService;
import com.africom.clientsales.web.rest.errors.BadRequestAlertException;
import com.africom.clientsales.service.dto.SuiviActionDTO;

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
 * REST controller for managing {@link com.africom.clientsales.domain.SuiviAction}.
 */
@RestController
@RequestMapping("/api")
public class SuiviActionResource {

    private final Logger log = LoggerFactory.getLogger(SuiviActionResource.class);

    private static final String ENTITY_NAME = "clientSalesSuiviAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuiviActionService suiviActionService;

    public SuiviActionResource(SuiviActionService suiviActionService) {
        this.suiviActionService = suiviActionService;
    }

    /**
     * {@code POST  /suivi-actions} : Create a new suiviAction.
     *
     * @param suiviActionDTO the suiviActionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suiviActionDTO, or with status {@code 400 (Bad Request)} if the suiviAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suivi-actions")
    public ResponseEntity<SuiviActionDTO> createSuiviAction(@Valid @RequestBody SuiviActionDTO suiviActionDTO) throws URISyntaxException {
        log.debug("REST request to save SuiviAction : {}", suiviActionDTO);
        if (suiviActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new suiviAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuiviActionDTO result = suiviActionService.save(suiviActionDTO);
        return ResponseEntity.created(new URI("/api/suivi-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /suivi-actions} : Updates an existing suiviAction.
     *
     * @param suiviActionDTO the suiviActionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suiviActionDTO,
     * or with status {@code 400 (Bad Request)} if the suiviActionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suiviActionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suivi-actions")
    public ResponseEntity<SuiviActionDTO> updateSuiviAction(@Valid @RequestBody SuiviActionDTO suiviActionDTO) throws URISyntaxException {
        log.debug("REST request to update SuiviAction : {}", suiviActionDTO);
        if (suiviActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SuiviActionDTO result = suiviActionService.save(suiviActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suiviActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /suivi-actions} : get all the suiviActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suiviActions in body.
     */
    @GetMapping("/suivi-actions")
    public ResponseEntity<List<SuiviActionDTO>> getAllSuiviActions(Pageable pageable) {
        log.debug("REST request to get a page of SuiviActions");
        Page<SuiviActionDTO> page = suiviActionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /suivi-actions/:id} : get the "id" suiviAction.
     *
     * @param id the id of the suiviActionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suiviActionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suivi-actions/{id}")
    public ResponseEntity<SuiviActionDTO> getSuiviAction(@PathVariable Long id) {
        log.debug("REST request to get SuiviAction : {}", id);
        Optional<SuiviActionDTO> suiviActionDTO = suiviActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suiviActionDTO);
    }

    /**
     * {@code DELETE  /suivi-actions/:id} : delete the "id" suiviAction.
     *
     * @param id the id of the suiviActionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suivi-actions/{id}")
    public ResponseEntity<Void> deleteSuiviAction(@PathVariable Long id) {
        log.debug("REST request to delete SuiviAction : {}", id);
        suiviActionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
