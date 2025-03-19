package com.africom.clientsales.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PanierBonusMapperTest {

    private PanierBonusMapper panierBonusMapper;

    @BeforeEach
    public void setUp() {
        panierBonusMapper = new PanierBonusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(panierBonusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(panierBonusMapper.fromId(null)).isNull();
    }
}
