package com.africom.productinventory.web.rest;

import com.africom.productinventory.service.CodeService;
import com.africom.productinventory.web.rest.errors.BadRequestAlertException;
import com.africom.productinventory.service.dto.CodeDTO;

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
 * REST controller for managing {@link com.africom.productinventory.domain.Code}.
 */
@RestController
@RequestMapping("/api")
public class CodeResource {

    private final Logger log = LoggerFactory.getLogger(CodeResource.class);

    private static final String ENTITY_NAME = "productInventoryCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeService codeService;

    public CodeResource(CodeService codeService) {
        this.codeService = codeService;
    }

    /**
     * {@code POST  /codes} : Create a new code.
     *
     * @param codeDTO the codeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeDTO, or with status {@code 400 (Bad Request)} if the code has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/codes")
    public ResponseEntity<CodeDTO> createCode(@Valid @RequestBody CodeDTO codeDTO) throws URISyntaxException {
        log.debug("REST request to save Code : {}", codeDTO);
        if (codeDTO.getId() != null) {
            throw new BadRequestAlertException("A new code cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodeDTO result = codeService.save(codeDTO);
        return ResponseEntity.created(new URI("/api/codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /codes} : Updates an existing code.
     *
     * @param codeDTO the codeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeDTO,
     * or with status {@code 400 (Bad Request)} if the codeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/codes")
    public ResponseEntity<CodeDTO> updateCode(@Valid @RequestBody CodeDTO codeDTO) throws URISyntaxException {
        log.debug("REST request to update Code : {}", codeDTO);
        if (codeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodeDTO result = codeService.save(codeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /codes} : get all the codes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codes in body.
     */
    @GetMapping("/codes")
    public ResponseEntity<List<CodeDTO>> getAllCodes(Pageable pageable) {
        log.debug("REST request to get a page of Codes");
        Page<CodeDTO> page = codeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /codes/:id} : get the "id" code.
     *
     * @param id the id of the codeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/codes/{id}")
    public ResponseEntity<CodeDTO> getCode(@PathVariable Long id) {
        log.debug("REST request to get Code : {}", id);
        Optional<CodeDTO> codeDTO = codeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codeDTO);
    }

    /**
     * {@code DELETE  /codes/:id} : delete the "id" code.
     *
     * @param id the id of the codeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/codes/{id}")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        log.debug("REST request to delete Code : {}", id);
        codeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
