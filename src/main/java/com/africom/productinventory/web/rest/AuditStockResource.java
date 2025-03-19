package com.africom.productinventory.web.rest;

import com.africom.productinventory.service.AuditStockService;
import com.africom.productinventory.web.rest.errors.BadRequestAlertException;
import com.africom.productinventory.service.dto.AuditStockDTO;

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
 * REST controller for managing {@link com.africom.productinventory.domain.AuditStock}.
 */
@RestController
@RequestMapping("/api")
public class AuditStockResource {

    private final Logger log = LoggerFactory.getLogger(AuditStockResource.class);

    private static final String ENTITY_NAME = "productInventoryAuditStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditStockService auditStockService;

    public AuditStockResource(AuditStockService auditStockService) {
        this.auditStockService = auditStockService;
    }

    /**
     * {@code POST  /audit-stocks} : Create a new auditStock.
     *
     * @param auditStockDTO the auditStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditStockDTO, or with status {@code 400 (Bad Request)} if the auditStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audit-stocks")
    public ResponseEntity<AuditStockDTO> createAuditStock(@Valid @RequestBody AuditStockDTO auditStockDTO) throws URISyntaxException {
        log.debug("REST request to save AuditStock : {}", auditStockDTO);
        if (auditStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditStockDTO result = auditStockService.save(auditStockDTO);
        return ResponseEntity.created(new URI("/api/audit-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audit-stocks} : Updates an existing auditStock.
     *
     * @param auditStockDTO the auditStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditStockDTO,
     * or with status {@code 400 (Bad Request)} if the auditStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audit-stocks")
    public ResponseEntity<AuditStockDTO> updateAuditStock(@Valid @RequestBody AuditStockDTO auditStockDTO) throws URISyntaxException {
        log.debug("REST request to update AuditStock : {}", auditStockDTO);
        if (auditStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuditStockDTO result = auditStockService.save(auditStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /audit-stocks} : get all the auditStocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditStocks in body.
     */
    @GetMapping("/audit-stocks")
    public ResponseEntity<List<AuditStockDTO>> getAllAuditStocks(Pageable pageable) {
        log.debug("REST request to get a page of AuditStocks");
        Page<AuditStockDTO> page = auditStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /audit-stocks/:id} : get the "id" auditStock.
     *
     * @param id the id of the auditStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audit-stocks/{id}")
    public ResponseEntity<AuditStockDTO> getAuditStock(@PathVariable Long id) {
        log.debug("REST request to get AuditStock : {}", id);
        Optional<AuditStockDTO> auditStockDTO = auditStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditStockDTO);
    }

    /**
     * {@code DELETE  /audit-stocks/:id} : delete the "id" auditStock.
     *
     * @param id the id of the auditStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audit-stocks/{id}")
    public ResponseEntity<Void> deleteAuditStock(@PathVariable Long id) {
        log.debug("REST request to delete AuditStock : {}", id);
        auditStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
