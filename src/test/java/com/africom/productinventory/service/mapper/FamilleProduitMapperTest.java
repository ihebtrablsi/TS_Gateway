package com.africom.productinventory.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FamilleProduitMapperTest {

    private FamilleProduitMapper familleProduitMapper;

    @BeforeEach
    public void setUp() {
        familleProduitMapper = new FamilleProduitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(familleProduitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(familleProduitMapper.fromId(null)).isNull();
    }
}
