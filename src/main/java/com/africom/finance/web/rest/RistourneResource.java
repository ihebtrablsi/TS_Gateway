package com.africom.finance.web.rest;

import com.africom.finance.service.RistourneService;
import com.africom.finance.web.rest.errors.BadRequestAlertException;
import com.africom.finance.service.dto.RistourneDTO;

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
 * REST controller for managing {@link com.africom.finance.domain.Ristourne}.
 */
@RestController
@RequestMapping("/api")
public class RistourneResource {

    private final Logger log = LoggerFactory.getLogger(RistourneResource.class);

    private static final String ENTITY_NAME = "financeRistourne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RistourneService ristourneService;

    public RistourneResource(RistourneService ristourneService) {
        this.ristourneService = ristourneService;
    }

    /**
     * {@code POST  /ristournes} : Create a new ristourne.
     *
     * @param ristourneDTO the ristourneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ristourneDTO, or with status {@code 400 (Bad Request)} if the ristourne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ristournes")
    public ResponseEntity<RistourneDTO> createRistourne(@Valid @RequestBody RistourneDTO ristourneDTO) throws URISyntaxException {
        log.debug("REST request to save Ristourne : {}", ristourneDTO);
        if (ristourneDTO.getId() != null) {
            throw new BadRequestAlertException("A new ristourne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RistourneDTO result = ristourneService.save(ristourneDTO);
        return ResponseEntity.created(new URI("/api/ristournes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ristournes} : Updates an existing ristourne.
     *
     * @param ristourneDTO the ristourneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ristourneDTO,
     * or with status {@code 400 (Bad Request)} if the ristourneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ristourneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ristournes")
    public ResponseEntity<RistourneDTO> updateRistourne(@Valid @RequestBody RistourneDTO ristourneDTO) throws URISyntaxException {
        log.debug("REST request to update Ristourne : {}", ristourneDTO);
        if (ristourneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RistourneDTO result = ristourneService.save(ristourneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ristourneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ristournes} : get all the ristournes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ristournes in body.
     */
    @GetMapping("/ristournes")
    public ResponseEntity<List<RistourneDTO>> getAllRistournes(Pageable pageable) {
        log.debug("REST request to get a page of Ristournes");
        Page<RistourneDTO> page = ristourneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ristournes/:id} : get the "id" ristourne.
     *
     * @param id the id of the ristourneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ristourneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ristournes/{id}")
    public ResponseEntity<RistourneDTO> getRistourne(@PathVariable Long id) {
        log.debug("REST request to get Ristourne : {}", id);
        Optional<RistourneDTO> ristourneDTO = ristourneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ristourneDTO);
    }

    /**
     * {@code DELETE  /ristournes/:id} : delete the "id" ristourne.
     *
     * @param id the id of the ristourneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ristournes/{id}")
    public ResponseEntity<Void> deleteRistourne(@PathVariable Long id) {
        log.debug("REST request to delete Ristourne : {}", id);
        ristourneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
