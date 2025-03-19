package com.africom.productinventory.service;

import com.africom.productinventory.domain.Code;
import com.africom.productinventory.repository.CodeRepository;
import com.africom.productinventory.service.dto.CodeDTO;
import com.africom.productinventory.service.mapper.CodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Code}.
 */
@Service
@Transactional
public class CodeService {

    private final Logger log = LoggerFactory.getLogger(CodeService.class);

    private final CodeRepository codeRepository;

    private final CodeMapper codeMapper;

    public CodeService(CodeRepository codeRepository, CodeMapper codeMapper) {
        this.codeRepository = codeRepository;
        this.codeMapper = codeMapper;
    }

    /**
     * Save a code.
     *
     * @param codeDTO the entity to save.
     * @return the persisted entity.
     */
    public CodeDTO save(CodeDTO codeDTO) {
        log.debug("Request to save Code : {}", codeDTO);
        Code code = codeMapper.toEntity(codeDTO);
        code = codeRepository.save(code);
        return codeMapper.toDto(code);
    }

    /**
     * Get all the codes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Codes");
        return codeRepository.findAll(pageable)
            .map(codeMapper::toDto);
    }


    /**
     * Get one code by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CodeDTO> findOne(Long id) {
        log.debug("Request to get Code : {}", id);
        return codeRepository.findById(id)
            .map(codeMapper::toDto);
    }

    /**
     * Delete the code by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Code : {}", id);
        codeRepository.deleteById(id);
    }
}
