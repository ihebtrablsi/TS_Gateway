package com.africom.clientsales.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LigneCommandeMapperTest {

    private LigneCommandeMapper ligneCommandeMapper;

    @BeforeEach
    public void setUp() {
        ligneCommandeMapper = new LigneCommandeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ligneCommandeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ligneCommandeMapper.fromId(null)).isNull();
    }
}
