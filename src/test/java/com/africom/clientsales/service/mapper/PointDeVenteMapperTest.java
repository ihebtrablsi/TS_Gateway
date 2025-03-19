package com.africom.clientsales.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PointDeVenteMapperTest {

    private PointDeVenteMapper pointDeVenteMapper;

    @BeforeEach
    public void setUp() {
        pointDeVenteMapper = new PointDeVenteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pointDeVenteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pointDeVenteMapper.fromId(null)).isNull();
    }
}
