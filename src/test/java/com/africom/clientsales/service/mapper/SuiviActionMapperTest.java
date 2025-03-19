package com.africom.clientsales.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SuiviActionMapperTest {

    private SuiviActionMapper suiviActionMapper;

    @BeforeEach
    public void setUp() {
        suiviActionMapper = new SuiviActionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(suiviActionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(suiviActionMapper.fromId(null)).isNull();
    }
}
