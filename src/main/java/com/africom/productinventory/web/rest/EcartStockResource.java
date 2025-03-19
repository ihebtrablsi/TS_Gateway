package com.africom.productinventory.web.rest;

import com.africom.productinventory.service.EcartStockService;
import com.africom.productinventory.web.rest.errors.BadRequestAlertException;
import com.africom.productinventory.service.dto.EcartStockDTO;

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
 * REST controller for managing {@link com.africom.productinventory.domain.EcartStock}.
 */
@RestController
@RequestMapping("/api")
public class EcartStockResource {

    private final Logger log = LoggerFactory.getLogger(EcartStockResource.class);

    private static final String ENTITY_NAME = "productInventoryEcartStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcartStockService ecartStockService;

    public EcartStockResource(EcartStockService ecartStockService) {
        this.ecartStockService = ecartStockService;
    }

    /**
     * {@code POST  /ecart-stocks} : Create a new ecartStock.
     *
     * @param ecartStockDTO the ecartStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecartStockDTO, or with status {@code 400 (Bad Request)} if the ecartStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ecart-stocks")
    public ResponseEntity<EcartStockDTO> createEcartStock(@Valid @RequestBody EcartStockDTO ecartStockDTO) throws URISyntaxException {
        log.debug("REST request to save EcartStock : {}", ecartStockDTO);
        if (ecartStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new ecartStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EcartStockDTO result = ecartStockService.save(ecartStockDTO);
        return ResponseEntity.created(new URI("/api/ecart-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ecart-stocks} : Updates an existing ecartStock.
     *
     * @param ecartStockDTO the ecartStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecartStockDTO,
     * or with status {@code 400 (Bad Request)} if the ecartStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecartStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ecart-stocks")
    public ResponseEntity<EcartStockDTO> updateEcartStock(@Valid @RequestBody EcartStockDTO ecartStockDTO) throws URISyntaxException {
        log.debug("REST request to update EcartStock : {}", ecartStockDTO);
        if (ecartStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EcartStockDTO result = ecartStockService.save(ecartStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ecartStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ecart-stocks} : get all the ecartStocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecartStocks in body.
     */
    @GetMapping("/ecart-stocks")
    public ResponseEntity<List<EcartStockDTO>> getAllEcartStocks(Pageable pageable) {
        log.debug("REST request to get a page of EcartStocks");
        Page<EcartStockDTO> page = ecartStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ecart-stocks/:id} : get the "id" ecartStock.
     *
     * @param id the id of the ecartStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecartStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ecart-stocks/{id}")
    public ResponseEntity<EcartStockDTO> getEcartStock(@PathVariable Long id) {
        log.debug("REST request to get EcartStock : {}", id);
        Optional<EcartStockDTO> ecartStockDTO = ecartStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecartStockDTO);
    }

    /**
     * {@code DELETE  /ecart-stocks/:id} : delete the "id" ecartStock.
     *
     * @param id the id of the ecartStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ecart-stocks/{id}")
    public ResponseEntity<Void> deleteEcartStock(@PathVariable Long id) {
        log.debug("REST request to delete EcartStock : {}", id);
        ecartStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
