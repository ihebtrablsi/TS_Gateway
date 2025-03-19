package com.africom.clientsales.web.rest;

import com.africom.clientsales.service.ActionCommercialeService;
import com.africom.clientsales.web.rest.errors.BadRequestAlertException;
import com.africom.clientsales.service.dto.ActionCommercialeDTO;

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
 * REST controller for managing {@link com.africom.clientsales.domain.ActionCommerciale}.
 */
@RestController
@RequestMapping("/api")
public class ActionCommercialeResource {

    private final Logger log = LoggerFactory.getLogger(ActionCommercialeResource.class);

    private static final String ENTITY_NAME = "clientSalesActionCommerciale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionCommercialeService actionCommercialeService;

    public ActionCommercialeResource(ActionCommercialeService actionCommercialeService) {
        this.actionCommercialeService = actionCommercialeService;
    }

    /**
     * {@code POST  /action-commerciales} : Create a new actionCommerciale.
     *
     * @param actionCommercialeDTO the actionCommercialeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionCommercialeDTO, or with status {@code 400 (Bad Request)} if the actionCommerciale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/action-commerciales")
    public ResponseEntity<ActionCommercialeDTO> createActionCommerciale(@Valid @RequestBody ActionCommercialeDTO actionCommercialeDTO) throws URISyntaxException {
        log.debug("REST request to save ActionCommerciale : {}", actionCommercialeDTO);
        if (actionCommercialeDTO.getId() != null) {
            throw new BadRequestAlertException("A new actionCommerciale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionCommercialeDTO result = actionCommercialeService.save(actionCommercialeDTO);
        return ResponseEntity.created(new URI("/api/action-commerciales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /action-commerciales} : Updates an existing actionCommerciale.
     *
     * @param actionCommercialeDTO the actionCommercialeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionCommercialeDTO,
     * or with status {@code 400 (Bad Request)} if the actionCommercialeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionCommercialeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/action-commerciales")
    public ResponseEntity<ActionCommercialeDTO> updateActionCommerciale(@Valid @RequestBody ActionCommercialeDTO actionCommercialeDTO) throws URISyntaxException {
        log.debug("REST request to update ActionCommerciale : {}", actionCommercialeDTO);
        if (actionCommercialeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionCommercialeDTO result = actionCommercialeService.save(actionCommercialeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionCommercialeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /action-commerciales} : get all the actionCommerciales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actionCommerciales in body.
     */
    @GetMapping("/action-commerciales")
    public ResponseEntity<List<ActionCommercialeDTO>> getAllActionCommerciales(Pageable pageable) {
        log.debug("REST request to get a page of ActionCommerciales");
        Page<ActionCommercialeDTO> page = actionCommercialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /action-commerciales/:id} : get the "id" actionCommerciale.
     *
     * @param id the id of the actionCommercialeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actionCommercialeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/action-commerciales/{id}")
    public ResponseEntity<ActionCommercialeDTO> getActionCommerciale(@PathVariable Long id) {
        log.debug("REST request to get ActionCommerciale : {}", id);
        Optional<ActionCommercialeDTO> actionCommercialeDTO = actionCommercialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionCommercialeDTO);
    }

    /**
     * {@code DELETE  /action-commerciales/:id} : delete the "id" actionCommerciale.
     *
     * @param id the id of the actionCommercialeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/action-commerciales/{id}")
    public ResponseEntity<Void> deleteActionCommerciale(@PathVariable Long id) {
        log.debug("REST request to delete ActionCommerciale : {}", id);
        actionCommercialeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
