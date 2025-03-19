package com.africom.finance.web.rest;

import com.africom.finance.service.IncentiveService;
import com.africom.finance.web.rest.errors.BadRequestAlertException;
import com.africom.finance.service.dto.IncentiveDTO;

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
 * REST controller for managing {@link com.africom.finance.domain.Incentive}.
 */
@RestController
@RequestMapping("/api")
public class IncentiveResource {

    private final Logger log = LoggerFactory.getLogger(IncentiveResource.class);

    private static final String ENTITY_NAME = "financeIncentive";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncentiveService incentiveService;

    public IncentiveResource(IncentiveService incentiveService) {
        this.incentiveService = incentiveService;
    }

    /**
     * {@code POST  /incentives} : Create a new incentive.
     *
     * @param incentiveDTO the incentiveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incentiveDTO, or with status {@code 400 (Bad Request)} if the incentive has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incentives")
    public ResponseEntity<IncentiveDTO> createIncentive(@Valid @RequestBody IncentiveDTO incentiveDTO) throws URISyntaxException {
        log.debug("REST request to save Incentive : {}", incentiveDTO);
        if (incentiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new incentive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncentiveDTO result = incentiveService.save(incentiveDTO);
        return ResponseEntity.created(new URI("/api/incentives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incentives} : Updates an existing incentive.
     *
     * @param incentiveDTO the incentiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incentiveDTO,
     * or with status {@code 400 (Bad Request)} if the incentiveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incentiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incentives")
    public ResponseEntity<IncentiveDTO> updateIncentive(@Valid @RequestBody IncentiveDTO incentiveDTO) throws URISyntaxException {
        log.debug("REST request to update Incentive : {}", incentiveDTO);
        if (incentiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncentiveDTO result = incentiveService.save(incentiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incentiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incentives} : get all the incentives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incentives in body.
     */
    @GetMapping("/incentives")
    public ResponseEntity<List<IncentiveDTO>> getAllIncentives(Pageable pageable) {
        log.debug("REST request to get a page of Incentives");
        Page<IncentiveDTO> page = incentiveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incentives/:id} : get the "id" incentive.
     *
     * @param id the id of the incentiveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incentiveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incentives/{id}")
    public ResponseEntity<IncentiveDTO> getIncentive(@PathVariable Long id) {
        log.debug("REST request to get Incentive : {}", id);
        Optional<IncentiveDTO> incentiveDTO = incentiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incentiveDTO);
    }

    /**
     * {@code DELETE  /incentives/:id} : delete the "id" incentive.
     *
     * @param id the id of the incentiveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incentives/{id}")
    public ResponseEntity<Void> deleteIncentive(@PathVariable Long id) {
        log.debug("REST request to delete Incentive : {}", id);
        incentiveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
