package com.africom.clientsales.web.rest;

import com.africom.clientsales.service.PointDeVenteService;
import com.africom.clientsales.web.rest.errors.BadRequestAlertException;
import com.africom.clientsales.service.dto.PointDeVenteDTO;

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
 * REST controller for managing {@link com.africom.clientsales.domain.PointDeVente}.
 */
@RestController
@RequestMapping("/api")
public class PointDeVenteResource {

    private final Logger log = LoggerFactory.getLogger(PointDeVenteResource.class);

    private static final String ENTITY_NAME = "clientSalesPointDeVente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointDeVenteService pointDeVenteService;

    public PointDeVenteResource(PointDeVenteService pointDeVenteService) {
        this.pointDeVenteService = pointDeVenteService;
    }

    /**
     * {@code POST  /point-de-ventes} : Create a new pointDeVente.
     *
     * @param pointDeVenteDTO the pointDeVenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointDeVenteDTO, or with status {@code 400 (Bad Request)} if the pointDeVente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-de-ventes")
    public ResponseEntity<PointDeVenteDTO> createPointDeVente(@Valid @RequestBody PointDeVenteDTO pointDeVenteDTO) throws URISyntaxException {
        log.debug("REST request to save PointDeVente : {}", pointDeVenteDTO);
        if (pointDeVenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new pointDeVente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointDeVenteDTO result = pointDeVenteService.save(pointDeVenteDTO);
        return ResponseEntity.created(new URI("/api/point-de-ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /point-de-ventes} : Updates an existing pointDeVente.
     *
     * @param pointDeVenteDTO the pointDeVenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointDeVenteDTO,
     * or with status {@code 400 (Bad Request)} if the pointDeVenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointDeVenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-de-ventes")
    public ResponseEntity<PointDeVenteDTO> updatePointDeVente(@Valid @RequestBody PointDeVenteDTO pointDeVenteDTO) throws URISyntaxException {
        log.debug("REST request to update PointDeVente : {}", pointDeVenteDTO);
        if (pointDeVenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PointDeVenteDTO result = pointDeVenteService.save(pointDeVenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointDeVenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /point-de-ventes} : get all the pointDeVentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointDeVentes in body.
     */
    @GetMapping("/point-de-ventes")
    public ResponseEntity<List<PointDeVenteDTO>> getAllPointDeVentes(Pageable pageable) {
        log.debug("REST request to get a page of PointDeVentes");
        Page<PointDeVenteDTO> page = pointDeVenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /point-de-ventes/:id} : get the "id" pointDeVente.
     *
     * @param id the id of the pointDeVenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointDeVenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-de-ventes/{id}")
    public ResponseEntity<PointDeVenteDTO> getPointDeVente(@PathVariable Long id) {
        log.debug("REST request to get PointDeVente : {}", id);
        Optional<PointDeVenteDTO> pointDeVenteDTO = pointDeVenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointDeVenteDTO);
    }

    /**
     * {@code DELETE  /point-de-ventes/:id} : delete the "id" pointDeVente.
     *
     * @param id the id of the pointDeVenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-de-ventes/{id}")
    public ResponseEntity<Void> deletePointDeVente(@PathVariable Long id) {
        log.debug("REST request to delete PointDeVente : {}", id);
        pointDeVenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
