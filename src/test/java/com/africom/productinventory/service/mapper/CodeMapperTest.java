package com.africom.productinventory.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CodeMapperTest {

    private CodeMapper codeMapper;

    @BeforeEach
    public void setUp() {
        codeMapper = new CodeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(codeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(codeMapper.fromId(null)).isNull();
    }
}
