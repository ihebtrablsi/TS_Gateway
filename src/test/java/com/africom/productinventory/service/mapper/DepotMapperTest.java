package com.africom.productinventory.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DepotMapperTest {

    private DepotMapper depotMapper;

    @BeforeEach
    public void setUp() {
        depotMapper = new DepotMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(depotMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(depotMapper.fromId(null)).isNull();
    }
}
